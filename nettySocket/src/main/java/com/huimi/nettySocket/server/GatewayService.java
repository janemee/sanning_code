package com.huimi.nettySocket.server;

import com.huimi.common.tools.StringUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GatewayService {

    private static Map<String, ChannelHandlerContext> map = new ConcurrentHashMap<>();

    public static void addGatewayChannel(String id, ChannelHandlerContext context) {
        if (null == map) {
            return ;
        }
        map.put(id, context);
    }

    public static Map<String, ChannelHandlerContext> getChannels() {
        return map;
    }

    public static ChannelHandlerContext getGatewayChannel(String id) {
        if(StringUtil.isBlank(id)) {
            return null;
        }
        if (null == map) {
            return null;
        }
        return map.get(id);
    }

    public static void removeGatewayChannel(String id) {
        if (null == map) {
            return;
        }
        map.remove(id);
    }
}
