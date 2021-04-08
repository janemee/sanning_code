package com.huimi.nettySocket.redis;

import com.huimi.core.constant.Constants;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter hotListenerAdapter, MessageListenerAdapter chatListenerAdapter
            , MessageListenerAdapter tiktokTaskListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫 live_hot 的通道
        container.addMessageListener(tiktokTaskListenerAdapter, new PatternTopic(Constants.TIKTOK_TASK));
        container.addMessageListener(hotListenerAdapter, new PatternTopic(Constants.TIKTOK_LIVE_HOT));
        container.addMessageListener(chatListenerAdapter, new PatternTopic(Constants.TIKTOK_LIVE_CHAT));
        //这个container 可以添加多个 messageListener
        return container;
    }


    /**
     * 消息监听器适配器，绑定消息处理器
     */
    @Bean
    MessageListenerAdapter hotListenerAdapter() {
        return new MessageListenerAdapter(new LiveHotChannelListener(), "onMessage");
    }

    /**
     * 消息监听器适配器，绑定消息处理器
     */
    @Bean
    MessageListenerAdapter chatListenerAdapter() {
        return new MessageListenerAdapter(new LiveChatChannelListener(), "onMessage");
    }

    /**
     * 消息监听器适配器，绑定消息处理器
     */
    @Bean
    MessageListenerAdapter tiktokTaskListenerAdapter() {
        return new MessageListenerAdapter(new TikTokTaskChannelListener(), "onMessage");
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
