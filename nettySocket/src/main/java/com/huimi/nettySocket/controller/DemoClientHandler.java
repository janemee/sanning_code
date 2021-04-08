package com.huimi.nettySocket.controller;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liupengr
 * @date 2020/2/16 16:11
 */
@Slf4j
public class DemoClientHandler extends SimpleChannelInboundHandler<String>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        log.info("demoClientHandler read msg:"+s);
    }
}