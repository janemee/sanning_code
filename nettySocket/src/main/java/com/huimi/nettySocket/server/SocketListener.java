
package com.huimi.nettySocket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SocketListener implements ServletContextListener {
	 private static final Logger logger = LoggerFactory.getLogger(SocketListener.class);

    private NettyServer server;

    // 应用关闭时，此方法被调用
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	logger.info("================1.启动时，开启监听========================");
        if (server == null) {
        	logger.info("================2.启动时，NettyServer为null，启动Netty服务========================");
            Thread thread = new Thread(new NettyServer());
            thread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	logger.info("================23========================");
    }

}
