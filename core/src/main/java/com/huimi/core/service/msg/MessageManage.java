package com.huimi.core.service.msg;


import cn.hutool.core.util.StrUtil;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.service.cache.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageManage {


    @Autowired
    private RedisService redisService;

    @Autowired
    private MessageZTManage messageZTManage;

    @Autowired
    private MessageYunPianManage messageYunPianManage;


    public void sendSms(String mobile, String content) {
        if (checkSmsOpen()) {
            // 短信通道
            String channel = StrUtil.isBlank(redisService.get(ConfigNID.SMS_CHANNEL)) ? "ZT" : redisService.get(ConfigNID.SMS_CHANNEL);
            switch (channel) {
                case "ZT":
                    // 助通短信通道
                    messageZTManage.sendYzm(mobile, content);
                    break;
                case "YP":
                    // 云片短信通道
                    messageYunPianManage.singleSend(mobile, content);
                    break;
                default:
                    // 默认助通通道
                    messageZTManage.sendYzm(mobile, content);
            }
        }
    }


    /**
     * 是否开启短信验证码
     *
     * @return
     */
    public boolean checkSmsOpen() {
        if ("true".equals(redisService.get(ConfigNID.SMS_ENABLED))) {
            return true;
        }
        return false;
    }
}
