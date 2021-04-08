package com.huimi.nettySocket.service;

import com.huimi.nettySocket.context.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractServiceAdapter implements IMessageService<String> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceAdapter.class);

    @Autowired
    private MessageContext messageContext;


    /**
     * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化。此方法必须在将类放入服务之前调用
     */
    @PostConstruct
    public void register() {
        messageContext.register(getCode(), this);
    }

    protected Map<String, Object> getTemplate(String message) {
        try {
            return null;
        } catch (Exception e) {
            logger.error("模板转换失败", e);
        }
        return Collections.emptyMap();
    }
}
