package com.huimi.nettySocket.controller;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DemoClient2 {

    private String ip;
    private int port;
    private int workerThreads; // 用于业务处理的计算线程

    public DemoClient2(String ip, int port, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.workerThreads = workerThreads;
    }

    public void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());
                    p.addLast(new DemoClientHandler());
                }
            });
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            future.channel().writeAndFlush("{\n" +
                    "\t\"option\": \"online\",\n" +
                    "\t\"data\": {\n" +
                    "\t\t\"device_code\": \"测试设备22\",\n" +
                    "\t\t\"invitation_code\": \"\",\n" +
                    "\t\t\"app_secret\": \"xxxxxx\"\n" +
                    "\t},\n" +
                    "\t\"device_id\": \"xxxxxx_22\"\n" +
                    "}");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
//        DemoClient client = new DemoClient("127.0.0.1", 58765, 1);
        DemoClient2 client = new DemoClient2("121.41.18.173", 58765, 1);
        client.start();
    }
}