package com.huimi.core.util;

import com.huimi.common.tools.DateUtil;
import com.huimi.core.service.cache.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 生成id工具类
 */
@Slf4j
public class GenerateIdUtil {

    private static String NAME = "contract:generateId:";//标识生成ID
    private static long EXPIRY = 60 * 60 * 3;//计数器失效时间5分钟
    private static long MAXCOUNT = 999999;//计数器最大位数 6位
    @Autowired
    private RedisService redisService;

    public String generateDbUuid() {
        String currDateStr = DateUtil.dateStr(new Date(), "yyyyMMddHH");
        String key = NAME + currDateStr;
        long count = redisService.getLong(key);
        if (count == -1) {
            //表示没有设置或者过期
            redisService.put(key, 1, EXPIRY);
            count = 1;
        } else if (count > MAXCOUNT) {
            //如果超过6位，删除该计数器key
            redisService.put(key, 1, EXPIRY);
            count = 1;
        } else {
            count = redisService.getLong(key) + 1;
            redisService.put(key, count);
        }
        return currDateStr + String.format("%06d", count);
    }

    /**
     * 生成数据库ID  总共18位(12+6) 不够用0补齐 右对齐
     *
     * @return
     */
    public static String generateDbId() {
        GenerateIdUtil generateIdUtil = new GenerateIdUtil();
        return generateIdUtil.generateDbUuid();
    }
}