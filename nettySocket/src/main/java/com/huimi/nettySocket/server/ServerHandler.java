package com.huimi.nettySocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.DateUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.Constants;
import com.huimi.core.constant.EnumConstants;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.task.TaskService;
import com.huimi.core.task.TaskModel;
import com.huimi.nettySocket.config.SpringContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端触发操作
 */
@Service
public class ServerHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    private static RedisService redisService = SpringContext.getBean(RedisService.class);

    /**
     * channelAction
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String uuid = ServerHandler.getChannelId(ctx, false);
        GatewayService.addGatewayChannel(uuid, ctx);
        LOGGER.info("--- A new connect come in,channelId is " + uuid);
    }

    /**
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String channelId = ServerHandler.getChannelId(ctx, false);
        //本地进程删除ctx对象实例  //TODO 暂时不删除
        GatewayService.removeGatewayChannel(channelId);
        //redis中删除通道id //TODO 暂时不清理
        String deviceId = redisService.get(Constants.CHANNEL_PREFIX + channelId);
        redisService.del(Constants.CHANNEL_PREFIX + channelId);
        redisService.del(Constants.DEVICE_CHANNEL + deviceId);
        LOGGER.warn("--- Netty Disconnect Client Channel Id Is :" + channelId + "/" + ctx.channel().remoteAddress());
        //更新设备状态
        EquipmentService equipmentService = SpringContext.getBean(EquipmentService.class);
        equipmentService.updateStateByChannelId(channelId);
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        String socketString = ctx.channel().remoteAddress().toString();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                LOGGER.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                LOGGER.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                LOGGER.info("Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
    }

    /**
     * 功能：读取客户端发送过来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        LOGGER.info("--- The channel reads the data sent by the client, channel id is " + ServerHandler.getChannelId(ctx, false));
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        buf.release();
        handleMessage(ctx, body);
    }

    private void handleMessage(ChannelHandlerContext ctx, String body) {
        String resJson = "";
        String deviceId = "";
        String channelId = ServerHandler.getChannelId(ctx, false);
        try {
            JSONObject object = JSON.parseObject(body);
            String option = object.getString("option");
            deviceId = object.getString("device_id");
            String data = object.getString("data");
            String platform = object.getString("platform");
            //区分平台 默认抖音
            if (StringUtil.isNotBlank(platform) && null != (EnumConstants.PLAT_FROM_TYPE.getEnumCodeOrValue(platform))) {
                platform = EnumConstants.PLAT_FROM_TYPE.getEnumCodeOrValue(platform).value;
            } else {
                platform = EnumConstants.PLAT_FROM_TYPE.TIKTOK.value;
            }
            JSONObject deviceData = JSON.parseObject(data);
            if (option.equalsIgnoreCase("online")) {
                String deviceCode = deviceData.getString("device_code");
                String invitationCode = deviceData.getString("invitation_code");
                String app_secret = deviceData.getString("app_secret");
                //把deviceId和uuid以key-value的形式保存到redis中
                redisService.put(Constants.DEVICE_CHANNEL + deviceId, channelId);
                //把UUID作为key deviceID作为value存放在redis中 方便连接断开时清除redis缓存
                redisService.put(Constants.CHANNEL_PREFIX + channelId, deviceId);
                EquipmentService equipmentService = SpringContext.getBean(EquipmentService.class);
                try {
                    equipmentService.selectByUid(deviceId, deviceCode, invitationCode, channelId);
                    resJson = JSON.toJSONString(ResultEntity.success("Device ID:" + deviceId + ", channelId is " + channelId + ", Connect Success"));
                } catch (Exception e) {
                    throw new BussinessException("Register Failed");
                }
                //获取未完成的任务 暂时只查询超级热度
                TaskService taskService = SpringContext.getBean(TaskService.class);
                List<TaskModel> list = taskService.findCodeTask(deviceId, EnumConstants.TaskType.LIVE_HOT, platform);
                if (null == list || list.size() == 0) {
                    //如果没有获取到超级热度则获取自动养号任务
                    list = taskService.findCodeTask(deviceId, EnumConstants.TaskType.AUTO, platform);
                }
                if (null != list && list.size() != 0) {
                    TaskModel taskModel = list.get(0);
                    Map<String, Object> map = new HashMap<>();
                    map.put("option", "task");
                    map.put("task_type", taskModel.getTask_type());
                    map.put("task_data", taskModel.getTask_data());
                    map.put("task_id", taskModel.getTask_id());
                    map.put("msg", "channelId is " + channelId + ", This device had a unfinished task,please keep executing!");
                    resJson = JSON.toJSONString(map);
                }
            } else if (option.equalsIgnoreCase("taskNotify")) {
                String taskType = deviceData.getString("task_type");
                String taskId = deviceData.getString("task_id");
                String taskStatus = deviceData.getString("task_status");
                EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
                if (type == null) {
                    resJson = JSON.toJSONString(ResultEntity.fail("Device id:" + deviceId + ", channelId is " + channelId + ". task type error, please confirm and retry."));
                } else {
                    if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
                        String key = taskType + ":" + deviceId;
                        redisService.del(key);
                    }
                    TaskService taskService = SpringContext.getBean(TaskService.class);
                    taskService.taskNotify(deviceId, type, taskId, taskStatus, "");
                }
            } else if (option.equalsIgnoreCase("hotSubTaskNotify")) {
                String taskType = deviceData.getString("task_type");
                String taskId = deviceData.getString("task_id");
                String taskStatus = deviceData.getString("task_status");
                EnumConstants.TaskType type = EnumConstants.TaskType.getTaskType(taskType);
                if (type == null) {
                    resJson = JSON.toJSONString(ResultEntity.fail("Device id:" + deviceId + ", channelId is " + channelId + ". task type error, please confirm and retry."));
                } else {
                    if (EnumConstants.taskStatus.DONE.code.equals(taskStatus)) {
                        String key = taskType + ":" + deviceId;
                        redisService.del(key);
                    }
                    TaskService taskService = SpringContext.getBean(TaskService.class);
                    taskService.hotSubTaskNotify(deviceId, type, taskId, taskStatus);
                }
            }
        } catch (Exception e) {
            if (e instanceof JSONException) {
                resJson = JSON.toJSONString(ResultEntity.fail("Device ID:" + deviceId + ", channelId is " + channelId + ", notify Failed, Cause: data not json."));
            } else if (e instanceof BussinessException) {
                resJson = JSON.toJSONString(ResultEntity.fail("Device ID:" + deviceId + ", channelId is " + channelId + ", notify Failed, Cause:" + e.getMessage()));
            } else {
                resJson = JSON.toJSONString(ResultEntity.fail("Device ID:" + deviceId + ", channelId is " + channelId + ", notify Failed, Cause:" + e.getCause()));
            }
        }
        LOGGER.info("--- The data returned to the channel(" + ServerHandler.getChannelId(ctx, false) + ") is:" + resJson);
        ctx.writeAndFlush(Unpooled.copiedBuffer(resJson.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Netty ExceptionCaught :" + ServerHandler.getChannelId(ctx, false) + " "
                + cause.getMessage() + "=======================", cause);
        ctx.close();

    }

    public static void sendToClient(String channelId, String message) {
        if (StringUtils.isBlank(channelId)) {
            LOGGER.error("channelId为空。丢失的信息:" + message);
            return;
        }
        ChannelHandlerContext ctx = GatewayService.getGatewayChannel(channelId);
        if (null == ctx) {
            LOGGER.error("没有找到通道id为:" + channelId + "的设备链接。丢失的信息:" + message);
            return;
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer((message).getBytes()));
        LOGGER.info("Send to channelId (" + channelId + ") is success, send time at " + DateUtil.dateStrMillis(new Date()));
    }


    public static String getChannelId(ChannelHandlerContext ctx, boolean isLongText) {
        if (null == ctx) {
            return "";
        }
        if (isLongText) {
            return ctx.channel().id().asLongText();
        }
        return ctx.channel().id().asShortText();
    }

}
