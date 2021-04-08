package com.huimi.nettySocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocketServerServiceImpl extends AbstractServiceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SocketServerServiceImpl.class);

    @Override
    public String handle(String requestId, String message) {
        try {
            // 1.读取配置中心模板
           Map<String, Object> map = getTemplate("读取模板开始");
            // 2.转换为统一出入平台报文
            // 3.发送http请求
            // 4.返回报文转换为dto
            //等等处理业务
            return message;
        } catch (Exception e) {
            logger.error("[交易处理异常]", e);
        }

        return null;
    }

    @Override
    public String getCode() {
        logger.info("================6.2 code1的值为：" + 201 + "========================");
        return "201";
    }

}
