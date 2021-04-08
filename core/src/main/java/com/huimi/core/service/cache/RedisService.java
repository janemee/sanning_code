package com.huimi.core.service.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.util.TradeUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by DFx-Dellbook on 2017/8/8
 */
@Service
public class RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        TradeUtil.setRedisService(this);
    }

    /**
     * 发布消息
     * @param channel 通道名称
     * @param message 消息
     */
    public void publish(String channel, Object message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

    /**
     * 保存
     */
    public void put(String key, Object val) {
        if (val != null) {
            stringRedisTemplate.opsForValue().set(key, formatPutStr(val));
        }
    }

    /**
     * 保存
     *
     * @param expire 过期时间 秒
     */
    public void put(String key, Object val, long expire) {
        if (val != null) {
            stringRedisTemplate.opsForValue().set(key, formatPutStr(val), expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取
     */
    public String get(String key) {
        return get(key, String.class);
    }


    /**
     * 获取
     */
    public int getInt(String key) {
        String dataStr = get(key, String.class);
        if (StringUtil.isBlank(dataStr)) {
            return -1;
        }
        return Integer.parseInt(dataStr);
    }

    /**
     * 获取
     */
    public long getLong(String key) {
        String dataStr = get(key, String.class);
        if (StringUtil.isBlank(dataStr)) {
            return -1;
        }
        return Long.parseLong(dataStr);
    }


    /**
     * 根据前缀获取所有前缀是key  的keys
     *
     * @param key 前缀
     */
    public Set<String> getKeys(String key) {
        if (StrUtil.isBlank(key)) return null;
        return stringRedisTemplate.keys(key + "*");
    }

    /**
     * 获取对象
     *
     * @param key   key
     * @param clazz 对象类型
     * @param <T>   对象类型
     */
    public <T> T get(String key, Class<T> clazz) {
        String val = stringRedisTemplate.opsForValue().get(key);
        return formatResultBean(val, clazz);
    }

    /**
     * 删除值
     */
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取list
     *
     * @param <T> list内对象
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        String val = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(val)) {
            return null;
        }
        return JSONUtil.parseArray(val).toList(clazz);
    }

    /**
     * 存放map
     *
     * @param key    map集合key
     * @param mapKey 集合内部key
     * @param mapVal 值
     */
    public void mapPut(String key, String mapKey, Object mapVal) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(mapKey) || mapVal == null) return;
        stringRedisTemplate.opsForHash().put(key, mapKey, formatPutStr(mapVal));
    }

    /**
     * 获取map
     *
     * @param key map集合key
     */
    public Map map(String key) {
        if (StrUtil.isBlank(key)) return null;
        return stringRedisTemplate.opsForHash().entries(key);
    }


    /**
     * 删除map
     *
     * @param key    map集合key
     * @param mapKey 集合内部key
     */
    public void mapDel(String key, String mapKey) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(mapKey)) return;
        stringRedisTemplate.opsForHash().delete(key, mapKey);
    }


    /**
     * 删除map
     *
     * @param key    map集合key
     * @param mapKey 集合内部key
     */
    public boolean mapHas(String key, String mapKey) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(mapKey)) return false;
        return stringRedisTemplate.opsForHash().hasKey(key, mapKey);
    }

    /**
     * 获取map内val
     *
     * @param key    外部key
     * @param mapKey 集合内部key
     */
    public String mapGet(String key, String mapKey) {
        return mapGet(key, mapKey, String.class);
    }


    /**
     * 获取map内val
     *
     * @param key    外部key
     * @param mapKey 集合内部key
     * @param clazz  值类型
     */
    public <T> T mapGet(String key, String mapKey, Class<T> clazz) {
        if (StrUtil.isBlank(key) || StrUtil.isBlank(mapKey)) return null;
        return formatResultBean(stringRedisTemplate.opsForHash().get(key, mapKey), clazz);
    }

    /**
     * 获得map对象所有key
     */
    public Set<String> mapKeys(String key) {
        if (StrUtil.isBlank(key)) return null;
        return stringRedisTemplate.opsForHash().keys(key).stream().map(Object::toString).collect(Collectors.toSet());
    }

    /**
     * 更新过期时间
     *
     * @param expire 单位(秒)
     */
    public void expire(String key, long expire) {
        stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 更新过期时间为30分钟
     */
    public void expire(String key) {
        expire(key, 30 * 60);
    }

    /**
     * 读取队列
     */
    public String pop(String name) {
        return listLeftPop(name, String.class);
    }

    /**
     * 读取队列
     */
    public <T> T pop(String name, Class<T> clazz) {
        return listLeftPop(name, clazz);
    }

    /**
     * 写入队列
     *
     * @param name  队列名称
     * @param value 值
     * @return 队列长度
     */
    public long push(String name, Object value) {
        return listRightPush(name, value);
    }

    /**
     * 判断key是否存在
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 向对应list尾部推送val
     */
    private long listRightPush(String key, Object val) {
        if (StrUtil.isBlank(key)) return 0;
        if (val == null) {
            return stringRedisTemplate.opsForList().size(key);
        }
        return stringRedisTemplate.opsForList().rightPush(key, formatPutStr(val));
    }

    /**
     * 从对应list头部获取val并删除
     */
    private <T> T listLeftPop(String key, Class<T> clazz) {
        Object val = stringRedisTemplate.opsForList().leftPop(key);
        return formatResultBean(val, clazz);
    }

    /**
     * 统一json转对象入口
     */
    private static <T> T formatResultBean(Object val, Class<T> clazz) {
        if (val == null) return null;
        if (clazz == String.class) return (T) val;
        return JSON.parseObject(val.toString(), clazz);
    }

    /**
     * 存入字符串
     */
    private static String formatPutStr(Object val) {
        if (val == null) return null;
        if (val instanceof String) return val.toString();
        if (val instanceof Number) return val.toString();
        return JSON.toJSONString(val);
    }

    public BigDecimal getBigDecimal(String key) {
        if (StringUtils.isBlank(key)) {
            throw new BussinessException("data is null");
        }
        String string = get(key);
        if (StringUtils.isBlank(string)) {
            throw new BussinessException("data is null");
        }
        return new BigDecimal(string);
    }
}
