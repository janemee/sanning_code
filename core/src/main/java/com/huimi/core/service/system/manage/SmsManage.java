package com.huimi.core.service.system.manage;

import cn.hutool.core.util.RandomUtil;
import com.huimi.common.utils.DateUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.constant.MsgTempNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.system.NoticeLogMapper;
import com.huimi.core.po.system.NoticeLog;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.msg.MessageManage;
import com.huimi.core.service.msg.MessageTemplateManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 短信专发
 */
@Slf4j
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class SmsManage {

    @Autowired
    private RedisService redisService;

    @Autowired
    private MessageTemplateManage messageTemplateManage;

    @Autowired
    private MessageManage messageManage;

    @Autowired
    private NoticeLogMapper noticeLogMapper;

    /**
     * 发送短信验证码 - 注册
     */
    public void sendRegistCode(String phone) {
        String nid = MsgTempNID.SMS_CODE_REGISTER_PHONE_CODE;
        String code = RandomUtil.randomNumbers(6);
        String smsSign = redisService.get(ConfigNID.SMS_SIGNATURE);
        String content = smsSign + messageTemplateManage.renderContent(nid, m -> m.put("code", code));
        try {
            messageManage.sendSms(phone, content);
            // 写入数据库
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDSUCCESS);
            int timeLimit = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_LIMIT));
            int timeOut = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_OUT));
            // 缓存发送间隔
            redisService.put(CacheID.CHECK_SEND_TIME_PREFIX + phone, phone, timeLimit);
            // 缓存有效期
            redisService.put(CacheID.REGIST_MOBILE_VERIFY_CODE_PREFIX + phone, code, timeOut * 60);
        } catch (BussinessException e) {
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDFAILURE);
            log.error("{}", e.getMessage(), e);
        }
    }

    /**
     * 发送短信验证码 - 忘记密码重置
     */
    public void sendBackPwdCode(String phone) {
        String nid = MsgTempNID.SMS_CODE_FORGOT_PASSWORD_CODE;
        String code = RandomUtil.randomNumbers(6);
        String smsSign = redisService.get(ConfigNID.SMS_SIGNATURE);
        String content = smsSign + messageTemplateManage.renderContent(nid, m -> m.put("code", code));

        try {
            messageManage.sendSms(phone, content);
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDSUCCESS);
            int timeLimit = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_LIMIT));
            int timeOut = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_OUT));
            // 缓存发送间隔
            redisService.put(CacheID.CHECK_SEND_TIME_PREFIX + phone, phone, timeLimit);
            // 缓存验证码
            redisService.put(CacheID.BACK_MOBILE_VERIFY_CODE_PREFIX + phone, code, timeOut * 60);
        } catch (BussinessException e) {
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDFAILURE);
            log.error("{}", e.getMessage(), e);
        }

    }

    /**
     * 发送短信验证码 - 修改绑定手机旧手机发送
     */
    public void sendModifyBindMobileCode(Integer userId, String phone) {
        String nid = MsgTempNID.SMS_CODE_BIND_MOBILE_CODE;
        String code = RandomUtil.randomNumbers(6);
        String smsSign = redisService.get(ConfigNID.SMS_SIGNATURE);
        String content = smsSign + messageTemplateManage.renderContent(nid, m -> m.put("code", code));

        try {
            messageManage.sendSms(phone, content);
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDSUCCESS);
            int timeLimit = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_LIMIT));
            int timeOut = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_OUT));
            // 缓存发送间隔
            redisService.put(CacheID.CHECK_SEND_TIME_PREFIX + phone, phone, timeLimit);
            // 缓存有效期
            redisService.put(CacheID.BIND_MOBILE_VERIFY_CODE_PREFIX + phone + ":" + userId, code, timeOut * 60);
        } catch (BussinessException e) {
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDFAILURE);
            log.error("{}", e.getMessage(), e);
        }
    }

    /**
     * 发送短信验证码 - 忘记支付密码发送
     */
    public void sendResetPayPwdCode(Integer userId, String phone) {
        String nid = MsgTempNID.SMS_CODE_RESET_PAY_PWD_CODE;
        String code = RandomUtil.randomNumbers(6);
        String smsSign = redisService.get(ConfigNID.SMS_SIGNATURE);
        String content = smsSign + messageTemplateManage.renderContent(nid, m -> m.put("code", code));
        try {
            messageManage.sendSms(phone, content);
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDSUCCESS);
            int timeLimit = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_LIMIT));
            int timeOut = Integer.parseInt(redisService.get(ConfigNID.SMS_YZM_TIME_OUT));
            // 缓存发送间隔
            redisService.put(CacheID.CHECK_SEND_TIME_PREFIX + phone, phone, timeLimit);
            // 缓存有效期
            redisService.put(CacheID.RESET_PAY_PWD_MOBILE_VERIFY_CODE_PREFIX + phone + ":" + userId, code, timeOut * 60);
        } catch (BussinessException e) {
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDFAILURE);
            log.error("{}", e.getMessage(), e);
        }

    }

    /**
     * 修改支付密码发送
     * 用户
     * 设置,重置,修改支付密码时发送
     *
     * @param userId
     * @param phone
     */
    public void sendModifyPayPwd(Integer userId, String phone) {
        String nid = MsgTempNID.SMS_MODIFY_PAY_PWD;
        String smsSign = redisService.get(ConfigNID.SMS_SIGNATURE);
        String content = smsSign + messageTemplateManage.renderContent(nid, m -> m.put("noticeTime", DateUtils.dateStr4(new Date())));
        try {
            messageManage.sendSms(phone, content);
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDSUCCESS);
        } catch (BussinessException e) {
            sendLog(phone, NoticeLog.ReceiverType.T1, NoticeLog.TYPE.SMS, content, NoticeLog.STATE.SENDFAILURE);
            log.error("{}", e.getMessage(), e);
        }
    }

    public void sendLog(String receiverAddress, NoticeLog.ReceiverType receiverType, NoticeLog.TYPE type, String content, NoticeLog.STATE state) {
        noticeLogMapper.insert(new NoticeLog(model -> {
            model.setContent(content);
            model.setType(type.code);
            model.setReceiverType(receiverType.code);
            model.setReceiveAddress(receiverAddress);
            model.setState(state.code);
            model.setCreateTime(new Date());
        }));
    }
}
