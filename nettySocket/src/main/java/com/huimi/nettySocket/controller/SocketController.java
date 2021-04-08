package com.huimi.nettySocket.controller;

import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.Constants;
import com.huimi.core.service.cache.RedisService;
import com.huimi.nettySocket.server.GatewayService;
import com.huimi.nettySocket.server.ServerHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class SocketController {

    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

    @Resource
    private RedisService redisService;

    @PostMapping(value = "/socket/getCurrAllChannelId")
    @ResponseBody
    public String getCurrAllChannelId() {
        Map<String, ChannelHandlerContext> channels = GatewayService.getChannels();
        return channels.keySet().toString();
    }

    @PostMapping(value = "/socket/sendToClient")
    @ResponseBody
    public String sendToClient(String channelId) {
        ChannelHandlerContext channelHandlerContext = GatewayService.getGatewayChannel(channelId);
        if (null == channelHandlerContext) {
            return "通道不存在";
        }
        String msg = "你的id是:" + channelId + ",知道吗？";
        boolean active = channelHandlerContext.channel().isActive();
        if(active) {
            channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer((msg).getBytes()));
            return "success";
        } else {
            return "通道不活跃了";
        }
    }

    @PostMapping(value = "/socket/clearInActiveChannel")
    @ResponseBody
    public String clearInActiveChannel() {
        Map<String, ChannelHandlerContext> channels = GatewayService.getChannels();
        int count = 0;
        for (Map.Entry<String, ChannelHandlerContext> ctx : channels.entrySet()) {
            boolean active = ctx.getValue().channel().isActive();
            if (!active) {
                count++;
                GatewayService.removeGatewayChannel(ctx.getKey());
                String key = ctx.getKey();
                String deviceId = redisService.get(Constants.CHANNEL_PREFIX + key);
                redisService.del(Constants.CHANNEL_PREFIX + key);
                redisService.del(Constants.DEVICE_CHANNEL + deviceId);
            }
        }
        return "总数:" + channels.size() + ", 清理不活跃的通道:" + count + "个,剩余:" + (channels.size() - count);
    }

    @PostMapping(value = "/socket/getChannelByChannelId")
    @ResponseBody
    public String getChannelByChannelId(String channelId) {
        ChannelHandlerContext gatewayChannel = GatewayService.getGatewayChannel(channelId);
        if (null == gatewayChannel) {
            return "没有获取到id为" + channelId + "的通道";
        }
        return ServerHandler.getChannelId(gatewayChannel, false);
    }

    @PostMapping(value = "/socket/getChannelByDeviceId")
    @ResponseBody
    public String getChannelByDeviceId(String deviceId) {
        String s = redisService.get(Constants.DEVICE_CHANNEL + deviceId);
        ChannelHandlerContext ctx = GatewayService.getGatewayChannel(s);
        if (null == ctx) {
            return "没有获取到设备id为" + deviceId + "的通道";
        }
        return ServerHandler.getChannelId(ctx, false);
    }


    @PostMapping(value = "/socket/sendToAllChannel")
    @ResponseBody
    public String sendToAllChannel() {
        String message;
        Map<String, ChannelHandlerContext> channels = GatewayService.getChannels();
        int count = 0;
        int scount = 0;
        for (Map.Entry<String, ChannelHandlerContext> ctx : channels.entrySet()) {
            String channelId = ServerHandler.getChannelId(ctx.getValue(), false);
            boolean active = ctx.getValue().channel().isActive();
            if (!active) {
                count++;
                System.out.println("send failed channel id is " + channelId);
            } else {
                message = "{\"code\":200,\"data\":[{\"task_data\":{\"comment_template\":{\"live\":\"hello client\"},\"device_code\":" +
                        "\"\",\"task_type\":\"live_chat\"},\"task_id\":\"1601185555901\",\"task_type\":\"live_chat\"},{\"device_code\":" +
                        "\"\",\"task_id\":\"1601185555901\",\"task_type\":\"live_chat\"}],\"msg\":\"success\",\"total\":2,\"url\":\"" +
                        "\",\"uuid\":\"" + channelId + "\"}";
                ctx.getValue().writeAndFlush(Unpooled.copiedBuffer((message).getBytes()));
                System.out.println("send success channel id is" + channelId);
                scount++;
            }
        }

        return "总数:" + channels.size() + ", 发送成功:" + scount + ", 失败:" + count;
    }

    @PostMapping(value = "/socket/init")
    @ResponseBody
    public String socketTest(HttpServletRequest request, String message) {
        logger.info("================10. http请求开始！！========================");
        String reqMsg = getReqString(request);
        if (StringUtils.isBlank(reqMsg)) {
            reqMsg = message;
        }
        Socket socket = null;
        PrintWriter pw = null;
        DataInputStream is = null;
        String retMsg;
        try {
            socket = new Socket("127.0.0.1", 58765);
            pw = new PrintWriter(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
            // 发送数据
            logger.info("================11. 发送Socket数据开始！！========================");
            pw.println(reqMsg);
            logger.info("================12. 发送Socket数据结束！！========================");
            pw.flush();
            socket.shutdownOutput();
            int len;
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
            }
            retMsg = sb.toString();
            logger.info("================19. 接受Socket服务端返回处理数据内容为：" + retMsg + "========================");
        } catch (Exception e) {
            logger.error("客户端代码发送失败!", e);
            retMsg = "请求处理发生异常，请稍后重试!";
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != pw) {
                    pw.close();
                }
                if (null != socket) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retMsg;
    }

    private String getReqString(HttpServletRequest req) {
        logger.info("[获取请求报文内容][开始]");

        String reqXml = "";
        // 输入流
        InputStream inputStream = null;
        // 输出流
        ByteArrayOutputStream outputStream = null;

        try {
            inputStream = req.getInputStream();
            outputStream = new ByteArrayOutputStream();
            // 创建缓冲区，大小1024字节
            byte[] buffer = new byte[1024];
            int len;
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
            reqXml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("[获取请求报文内容][发生异常][异常为：" + e.getMessage() + "]" + e);
        } finally {
            // 关闭输入流
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            // 关闭输出流
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        logger.info("[获取请求报文内容][报文内容为：" + reqXml + "]");
        logger.info("[获取请求报文内容][结束]");
        return reqXml;
    }

}
