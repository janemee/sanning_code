package com.huimi.nettySocket.context;

import com.huimi.nettySocket.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageContext.class);

    private Map<String, IMessageService<String>> handlers = new HashMap<>();

    public void register(String type, IMessageService<String> handler) {
        handlers.put(type, handler);
    }

    public IMessageService<String> get(String type) {
        return handlers.get(type);
    }

}
