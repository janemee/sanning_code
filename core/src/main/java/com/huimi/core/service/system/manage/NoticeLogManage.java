package com.huimi.core.service.system.manage;


import com.huimi.core.constant.MsgTempNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.mapper.system.NoticeLogMapper;
import com.huimi.core.po.system.NoticeLog;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.service.msg.MessageTemplateManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 站内信专发
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class NoticeLogManage {

    @Autowired
    private RedisService redisService;

    @Autowired
    private NoticeLogMapper noticeLogMapper;



    @Autowired
    private MessageTemplateManage messageTemplateManage;



//================================= 用户站内信 开始 =============================

    /**
     * 用户
     * 提现申请
     *
     * @param userId
     * @param amount
     */
    public void sendCashApply(Integer userId, BigDecimal amount) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_CASH_APPLY;
        String content = messageTemplateManage.renderContent(nid, data -> {
            data.put("amount", amount.toString());
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

    /**
     * 用户
     * 提现成功
     *
     * @param userId
     * @param amount
     * @param fee
     * @param actualAmount
     */
    public void sendCashSuccess(Integer userId, BigDecimal amount, BigDecimal fee, BigDecimal actualAmount) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_CASH_SUCCESS;
        String content = messageTemplateManage.renderContent(nid, data -> {
            data.put("amount", amount.toString());
            data.put("fee", fee.toString());
            data.put("actualAmount", actualAmount.toString());
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

    /**
     * 用户
     * 提现失败
     *
     * @param userId
     * @param amount
     * @param remark
     */
    public void sendCashFailed(Integer userId, BigDecimal amount, String remark) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_CASH_FAILED;
        String content = messageTemplateManage.renderContent(nid, data -> {
            data.put("amount", amount.toString());
            data.put("remark", remark);
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }


    /**
     * 用户
     * 提现退汇
     *
     * @param userId
     * @param amount
     * @param remark
     */
    public void sendCashTuihui(Integer userId, BigDecimal amount, String remark) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_CASH_TUIHUI;
        String content = messageTemplateManage.renderContent(nid, data -> {
            data.put("amount", amount.toString());
            data.put("remark", remark);
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

    /**
     * 用户
     * 实名认证 申请
     *
     * @param userId
     */
    public void sendIdentifyApply(Integer userId) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_IDENTIFY_APPLY;
        String content = messageTemplateManage.renderContent(nid, data -> {
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

    /**
     * 用户
     * 实名认证 成功
     *
     * @param userId
     */
    public void sendIdentifySuccess(Integer userId) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_IDENTIFY_SUCCESS;
        String content = messageTemplateManage.renderContent(nid, data -> {
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

    /**
     * 用户
     * 实名认证 失败
     *
     * @param userId
     * @param remark
     */
    public void sendIdentifyFailed(Integer userId, String remark) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;

        int receiverType = NoticeLog.ReceiverType.T1.code;
        String nid = MsgTempNID.LETTER_IDENTIFY_FAILED;
        String content = messageTemplateManage.renderContent(nid, data -> {
            data.put("remark", remark);
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }










//============================= 用户站内信 结束 ================================


    /**
     * 发送站内信
     *
     * @param userId
     * @param receiverType
     * @param title
     * @param content
     * @param txType
     * @param txId
     */
    public void sendLog(Integer userId, Integer receiverType, String title, String content, NoticeLog.TxType txType, Integer txId) {
        NoticeLog log = new NoticeLog();
        log.setReceiverId(userId);
        log.setReceiverType(receiverType);
        log.setTitle(title);
        log.setContent(content);
        log.setType(NoticeLog.TYPE.INLETTER.code);
        log.setState(NoticeLog.STATE.UNREAD.code);
        if (null != txType) {
            log.setTxType(txType.code);
            if (null != txId) {
                log.setTxId(txId);
            }
        }
        log.setCreateTime(new Date());
        int insertCount = noticeLogMapper.insert(log);
        if (insertCount != 1) {
            throw new BussinessException("发送站内信失败");
        }
    }

    public void sendLogs(List<NoticeLog> logs) {
        int insertCount = noticeLogMapper.insertList(logs);
        if (insertCount != logs.size()) {
            throw new BussinessException("发送站内信失败");
        }
    }

    /**
     * 商家
     * 实名认证 申请
     *
     * @param userId
     */
    public void sendShopIdentifyApply(Integer userId) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T3.code;
        String nid = MsgTempNID.LETTER_IDENTIFY_APPLY;
        String content = messageTemplateManage.renderContent(nid, data -> {
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

    /**
     * 商家
     * 实名认证 失败
     *
     * @param userId
     * @param remark
     */
    public void sendShopIdentifyFailed(Integer userId, String remark) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;

        int receiverType = NoticeLog.ReceiverType.T3.code;
        String nid = MsgTempNID.LETTER_IDENTIFY_FAILED;
        String content = messageTemplateManage.renderContent(nid, data -> {
            data.put("remark", remark);
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }


    /**
     * 商家
     * 实名认证 成功
     *
     * @param userId
     */
    public void sendShopIdentifySuccess(Integer userId) {
        String title = "系统通知";
        NoticeLog.TxType txType = NoticeLog.TxType.T1;
        int receiverType = NoticeLog.ReceiverType.T3.code;
        String nid = MsgTempNID.LETTER_IDENTIFY_SUCCESS;
        String content = messageTemplateManage.renderContent(nid, data -> {
        });
        sendLog(userId, receiverType, title, content, txType, null);
    }

}
