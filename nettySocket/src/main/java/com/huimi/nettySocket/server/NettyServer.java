
package com.huimi.nettySocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * socket监听服务
 *
 * @author xu
 */
public class NettyServer implements Runnable {

    /**
     * 异常输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    /**
     * socket监听
     */
    public static void socketListener() {
        LOGGER.info("5.NettyServer开始启动");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        int port = 58765;
        try {
            LOGGER.info("7.1 NettyServer 端口为：" + port);
            ServerBootstrap bootstrap = createBootStrap(bossGroup, workerGroup);
            LOGGER.info("8.NettyServer 启动中.....");
            ChannelFuture channelFuture = bootstrap.bind(port).sync(); // 服务器异步创建绑定
            LOGGER.info("9.NettyServer 启动完毕！！");
            //closeFuture()是开启了一个子线程server channel的监听器，负责监听channel是否关闭的状态，syncUninterruptibly()让主线程同步等待子线程结果。
            channelFuture.channel().closeFuture().sync(); // 关闭服务器通道
            LOGGER.info("41.NettyServer 关闭服务器通道！！");
        } catch (Exception e) {
            LOGGER.error("4.1 NettyServer 端口为：" + port + " 启动出现异常， 异常内容为：" + e.getMessage());
        } finally {
            LOGGER.error("4.2 NettyServer 服务关闭");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static ServerBootstrap createBootStrap(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)  //绑定线程池
                .channel(NioServerSocketChannel.class)  // 指定使用的channel
                //有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                // reuse addr，避免端口冲突
                .option(ChannelOption.SO_REUSEADDR, true)
                //保持连接数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 7200)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //用于Channel分配接受Buffer的分配器，默认值为AdaptiveRecvByteBufAllocator.DEFAULT，是一个自适应的接受缓冲区分配器，能根据接受到的数据自动调节大小
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 65535, 65535))
                .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler()); // 客户端触发操作
                        ch.pipeline().addLast("bytesEncoder", new ByteArrayEncoder());
                        ch.pipeline().addLast(new ExceptionHandler());

                    }
                });
        return bootstrap;
    }

    /**
     * @see Runnable#run()
     */
    @Override
    public void run() {
        LOGGER.info(" 4.多线程启动Netty Server ");
        NettyServer.socketListener();
    }

    public static class ExceptionHandler extends ChannelDuplexHandler {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            ctx.write(msg, promise.addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    LOGGER.error(future.channel().id() + " - send data to client exception occur: ", future.cause());
                }
            }));
        }
    }
}


