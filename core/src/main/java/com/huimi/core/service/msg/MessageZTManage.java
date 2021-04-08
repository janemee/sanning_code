package com.huimi.core.service.msg;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.huimi.common.utils.StringUtils;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.util.ztinfo.MD5Gen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 助通短信相关
 */
@Slf4j
@Component
@Transactional(rollbackFor = Throwable.class)
public class MessageZTManage {

    @Autowired
    private RedisService redisService;

    private static final String URL = "http://api.zthysms.com/sendSms.do";

    /**
     * 发送一条验证码短信
     *
     * @param mobilePhone 手机号
     * @param content     内容
     */
    public void sendYzm(String mobilePhone, String content) {
        Map<String, Object> param = new HashMap<>();
        // 发送日期
        String tkey = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        // 账户用户名
        String userName = redisService.get(ConfigNID.SMS_YZM_USERNAME);
        // 密码
        String password = redisService.get(ConfigNID.SMS_YZM_PASSWORD);
        String pwdMd5 = MD5Gen.getMD5(MD5Gen.getMD5(password) + tkey);

        param.put("url", URL);
        param.put("username", userName);
        param.put("password", pwdMd5);
        param.put("tkey", tkey);
        param.put("mobile", mobilePhone);
        param.put("content", content);
        param.put("xh", "");
        String result = HttpUtil.post(URL, param);
        if (StringUtils.isBlank(result)) {
            throw new BussinessException("短信发送失败");
        }

        String[] s = StrUtil.splitToArray(result, ',');
        if (!s[0].equals("1")) {
            log.error("助通短信发送失败,第三方返回:" + result);
            throw new BussinessException("短信发送失败");
        }
    }

}
