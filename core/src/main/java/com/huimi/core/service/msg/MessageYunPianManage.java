package com.huimi.core.service.msg;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 云片网短信接口
 */
@Slf4j
@Component
public class MessageYunPianManage {


    @Autowired
    private RedisService redisService;


    public static final String URL = "https://sms.yunpian.com/v2/sms/single_send.json";


    /**
     * 发送一条验证码短信
     *
     * @param phone   手机号
     * @param content 内容
     */
    public void singleSend(String phone, String content) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", redisService.get(ConfigNID.SMS_YP_APIKEY));
        params.put("text", content);
        params.put("mobile", phone);
        String result = HttpUtil.post(URL, params);

        if (StringUtils.isBlank(result)) {
            throw new BussinessException("短信发送失败");
        }

        JSONObject jo = JSON.parseObject(result);
        if (!"0".equals(jo.get("code").toString())) {
            log.error("云片短信发送失败,返回:" + result);
            throw new BussinessException(jo.get("msg").toString());
        }
    }
}
