package com.huimi.core.service.cache;

import com.huimi.core.exception.BussinessException;
import com.huimi.common.entity.ResultEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用于页面响应消息(各種页面同步/异步接口返回消息)
 */
@Service
public class MsgService {
    @Resource
    private RedisService redisService;

    @AllArgsConstructor
    public enum MsgKey {
        /**
         * 投资进程返回消息
         */
        TENDER("tender", "MSG:TENDER:"),
        /** 转让进程返回消息 */
        TRANSFER("transfer", "MSG:TRANSFER:");
        public String type;
        public String key;

        public static MsgKey findByType(String type) {
            for (MsgKey mk : MsgKey.values()) {
                if (mk.type.equals(type)) return mk;
            }
            throw new BussinessException("参数错误");
        }
    }

    /**
     * 获得消息
     *
     * @param msgKey 消息类型
     * @param key
     * @return
     */
    public ResultEntity get(MsgKey msgKey, String key) {
        return redisService.get(msgKey.key + key, ResultEntity.class);
    }

    /**
     * 放置消息
     *
     * @param msgKey 消息类型
     * @param key
     * @param msg    消息
     * @param expire 过期时间
     */
    public void put(MsgKey msgKey, String key, ResultEntity msg, int expire) {
        redisService.put(msgKey.key + key, msg, expire);
    }

    /**
     * 放置成功消息( 默认过期时间30秒)
     *
     * @param msgKey 消息类型
     * @param key
     * @param msg
     * @param uri    地址
     */
    public void putSucc(MsgKey msgKey, String key, String msg, String uri) {
        ResultEntity re = new ResultEntity();
        re.setCode(ResultEntity.SUCCESS);
        re.setMsg(msg);
        re.setUrl(uri);
        put(msgKey, key, re, 30);
    }


    /**
     * 放置成功消息( 默认过期时间30秒)
     *
     * @param msgKey 消息类型
     * @param key 键
     * @param data 数据
     */
    public void putSucc(MsgKey msgKey, String key, Object data) {
        ResultEntity re = new ResultEntity();
        re.setCode(ResultEntity.SUCCESS);
        re.setData(data);
        put(msgKey, key, re, 30);
    }

    /**
     * 放置失败消息( 默认过期时间30秒)
     *
     * @param msgKey 消息类型
     * @param key
     * @param msg
     * @param uri    地址
     */
    public void putFail(MsgKey msgKey, String key, String msg, String uri) {
        ResultEntity re = new ResultEntity();
        re.setCode(ResultEntity.FAIL);
        re.setMsg(msg);
        re.setUrl(uri);
        put(msgKey, key, re, 30);
    }

    /**
     * 放置等待消息( 默认过期时间30秒)
     *
     * @param msgKey 消息类型
     * @param key
     */
    public void putLoading(MsgKey msgKey, String key) {
        ResultEntity re = new ResultEntity();
        re.setCode(ResultEntity.LOADING);
        put(msgKey, key, re, 30);
    }
}
