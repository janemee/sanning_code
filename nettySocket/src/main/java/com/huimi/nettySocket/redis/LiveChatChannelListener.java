package com.huimi.nettySocket.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huimi.common.entity.ResultEntity;
import com.huimi.common.tools.StringUtil;
import com.huimi.core.po.equipment.Equipment;
import com.huimi.core.service.equipment.EquipmentService;
import com.huimi.core.service.liveHotSubTask.LiveHotSubTaskService;
import com.huimi.nettySocket.config.SpringContext;
import com.huimi.nettySocket.server.GatewayService;
import com.huimi.nettySocket.server.ServerHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
/**
 * 基于超级热度下的子任务通道
 */
public class LiveChatChannelListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LiveHotChannelListener.class);

    @Override
    public void onMessage(Message message, byte[] bytes) {
        long s1 = System.currentTimeMillis();
        ResultEntity resultEntity = JSON.parseObject(message.toString(), ResultEntity.class);
        Map<String, Object> map = new HashMap<>();
        map.put("option", "sub_task");
        JSONArray data = (JSONArray) resultEntity.getData();
        JSONObject object = JSON.parseObject(data.get(0).toString());
        map.put("task_type", object.get("task_type"));
        map.put("task_data", object.get("task_data"));
        Object task_id = object.get("task_id");
        map.put("task_id", task_id);
        int state = 1;
        String channelId = resultEntity.getUuid();
        //取消tcp通道发送
        if (StringUtil.isBlank(channelId)) {
            String device_code = object.getString("device_code");
            Equipment equipment = SpringContext.getBean(EquipmentService.class).selectByUid(device_code);
            if(null == equipment) {
                state = 3;
                LOGGER.error("channelId丢失。取消发送:");
            } else {
                channelId = equipment.getChannelId();
            }
        } else {
            ChannelHandlerContext ctx = GatewayService.getGatewayChannel(channelId);
            if (null == ctx) {
                state = 2;
                LOGGER.error("没有找到通道id为:" + channelId + "的设备链接。取消发送:");
            } else {
                ServerHandler.sendToClient(channelId, JSON.toJSONString(map));
                System.out.println("sendToClient took:" + (System.currentTimeMillis() - s1));
            }
        }
        //更新任务发送状态
        SpringContext.getBean(LiveHotSubTaskService.class).updateByUuid(task_id.toString(), System.currentTimeMillis() - s1, state);
    }
}
