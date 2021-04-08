package com.huimi.nettySocket.redis;

import com.huimi.core.constant.Constants;
import com.huimi.core.service.cache.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 测试
 */
@EnableScheduling //开启定时器功能
@Component
public class MessageSender {

    @Resource
    RedisService redisService;

//    @Scheduled(fixedRate = 3000) //间隔2s 通过StringRedisTemplate对象向redis消息队列cat频道发布消息
    public void sendCatMessage(){
        //循环给redis中存在的通道id删除
        Set<String> keys = redisService.getKeys(Constants.DEVICE_CHANNEL);
    }
}
