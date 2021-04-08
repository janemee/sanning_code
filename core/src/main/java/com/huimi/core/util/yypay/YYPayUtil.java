package com.huimi.core.util.yypay;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huimi.common.utils.OrderNoUtils;
import com.huimi.common.utils.SpringContextUtils;
import com.huimi.core.constant.CacheID;
import com.huimi.core.constant.ConfigNID;
import com.huimi.core.exception.BussinessException;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.util.yypay.model.PayChannel;
import com.huimi.core.util.yypay.utils.ProcessMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class YYPayUtil {

    public static final String INTERFACE_GETBANKS = "getBanksForPay";
    public static final String INTERFACE_PAYORDER = "anonymousPayOrder";
    public static final String INTERFACE_DAIFU = "danbihuikuan";
    public static final String INTERFACE_TUIHUI = "submerStateURLManage";
    public static final String VERSION = "B2C1.0";

    public static final String MERCHANTID_TEST = "M100001563";
    public static final String URL_GETBANKS_TEST = "http://testpay.yoyipay.com:28080/pay/getBanksForPay.do";
    public static final String URL_PAYORDER_TEST = "http://testpay.yoyipay.com:28080/pay/anonymousPayOrder.do";
    public static final String URL_DAIFU_TEST = "http://testpay.yoyipay.com:28080/pay/danbihuikuan.do";
    public static final String URL_TUIHUI_TEST = "http://testpay.yoyipay.com:28080/ecs/submerstatenotifyurlmanage.do";
    private static final String KEY_TEST = "123123";//商户签名密钥
    private static String PFX_PATH_TEST = "config/yypayTestNew.pfx";
    private static String CERT_PATH_TEST = "config/yypayTestNew.cer";
    private static String PASSWORD_TEST = "1q2w3e4r";

    public static final String MERCHANTID = "M100002975";
    public static final String URL_GETBANKS = "https://pay.yoyipay.com/pay/getBanksForPay.do";
    public static final String URL_PAYORDER = "https://pay.yoyipay.com/pay/anonymousPayOrder.do";
    public static final String URL_DAIFU = "https://pay.yoyipay.com/pay/danbihuikuan.do";
    public static final String URL_TUIHUI = "https://my.yoyipay.com/ecs/submerstatenotifyurlmanage.do";
    private static final String KEY = "102938";//商户签名密钥
    private static String PFX_PATH = "config/yoyi.pfx";
    private static String CERT_PATH = "config/yoyiNew.cer";
    private static String PASSWORD = "102938";


    private static RedisService redisService = SpringContextUtils.getBean(RedisService.class);


    /**
     * 查询支付通道
     *
     * @return
     * @throws Exception
     */
    public static List<PayChannel> getBanksForPay() throws Exception {
        String sourceData = "<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq><remark></remark></B2CReq>";
        String base64Data = new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK")));
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceName", INTERFACE_GETBANKS);
        params.put("version", VERSION);
        params.put("tranData", base64Data);
        params.put("merSignMsg", ProcessMessage.signMessage(sourceData, PFX_PATH, PASSWORD));
        params.put("merchantId", MERCHANTID);

        String result = HttpUtil.post(URL_GETBANKS, params);
        String resultData = new String(ProcessMessage.Base64Decode(result), "GBK");
        log.info("请求支付渠道结果：{}", resultData);
        List<PayChannel> channels = PayChannel.getList(resultData);
        redisService.put(CacheID.YYPAY_CHANNEL, channels);
        return channels;
    }

    public static void main(String[] args) throws Exception {
        YYPayUtil.getBanksForPay();
    }

    /**
     * 提交退汇地址
     *
     * @return
     * @throws Exception
     */
    public static void doTuiHuiDemo() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("payType", "2");// 2=结算/提现退汇通知
        params.put("notifyURL", "http://20662wm191.iok.la/api/web/notify/thNotify");

        String sourceData = getSourceDataForTH(params);

        String base64Data = new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK")));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("interfaceName", INTERFACE_TUIHUI);
        paramMap.put("version", VERSION);
        paramMap.put("tranData", base64Data);
        paramMap.put("merSignMsg", ProcessMessage.signMessage(sourceData, PFX_PATH_TEST, "1q2w3e4r"));
        paramMap.put("merchantId", "M100001563");

        String result = HttpUtil.post(URL_TUIHUI_TEST, paramMap);
        JSONObject jo = JSONUtil.parseObj(result);
        String errorCode = jo.get("errorCode").toString();
        String errorMsg = jo.get("errorMsg").toString();
        log.info("退汇通知地址维护应答码：{}，应答结果：{}", errorCode, errorMsg);
        if (!"0".equals(errorCode)) {
            throw new BussinessException(errorMsg);
        }
    }

    /**
     * 提交代付
     *
     * @return
     * @throws Exception
     */
    public static void doSingleDFDemo() throws Exception {
        Map<String, String> params = new HashMap<>();
        String orderNo = OrderNoUtils.getSerialNumber();
        System.out.println(orderNo);
        params.put("recAcct", "6216261000000000018");// 收款的银行账户
        params.put("orderAmt", "1.00");// 汇款的具体金额
        params.put("accountName", "全渠道");// 收款客户名
        params.put("bankName", "平安银行");// 收款银行的名称
        params.put("notifyURL", "http://20662wm191.iok.la/api/web/notify/dfNotify");
        params.put("merFlowNo", orderNo);// 商户流水号
        params.put("userIp", "10.0.0.15");// 访问ip地址

        String sourceData = getSourceDataForDF(params);

        String base64Data = new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK")));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("interfaceName", INTERFACE_DAIFU);
        paramMap.put("version", VERSION);
        paramMap.put("tranData", base64Data);
        paramMap.put("merSignMsg", ProcessMessage.signMessage(sourceData, PFX_PATH_TEST, "1q2w3e4r"));
        paramMap.put("merchantId", "M100001563");

        String result = HttpUtil.post(URL_DAIFU_TEST, paramMap);
        JSONObject jo = JSONUtil.parseObj(result);
        String errorCode = jo.get("errorCode").toString();
        String errorMsg = jo.get("errorMsg").toString();
        String tranDataBase64 = jo.get("tranData").toString();
        String merSignMsg = jo.get("merSignMsg").toString();
        String tranDataGBK = new String(ProcessMessage.Base64Decode(tranDataBase64), "GBK");//通知base64解密用GBK
        log.info("代付申请应答码：{}，应答结果：{}，业务受理结果：{}", errorCode, errorMsg, tranDataGBK);
        if (!YYPayUtil.verifyMessage(tranDataGBK, merSignMsg)) {
            throw new BussinessException("代付应答信息验签失败");
        }
        if (!"0".equals(errorCode)) {
            throw new BussinessException(errorMsg);
        }
        if (!"0000".equals(getNodeValue(tranDataGBK, "returnCode"))) {
            throw new BussinessException("代付申请受理失败");
        }
    }



    /**
     * 提交退汇地址
     *
     * @return
     * @throws Exception
     */
    public static void submitTuihuiUrl() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("payType", "2");// 2=结算/提现退汇通知
        params.put("notifyURL", redisService.get(ConfigNID.WEB_SERVER_URL) + "/api/web/notify/thNotify");

        String sourceData = getSourceDataForTH(params);

        String base64Data = new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK")));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("interfaceName", INTERFACE_TUIHUI);
        paramMap.put("version", VERSION);
        paramMap.put("tranData", base64Data);
        paramMap.put("merSignMsg", ProcessMessage.signMessage(sourceData, PFX_PATH, PASSWORD));
        paramMap.put("merchantId", MERCHANTID);

        String result = HttpUtil.post(URL_TUIHUI, paramMap);
        JSONObject jo = JSONUtil.parseObj(result);
        String errorCode = jo.get("errorCode").toString();
        String errorMsg = jo.get("errorMsg").toString();
        log.info("退汇通知地址维护应答码：{}，应答结果：{}", errorCode, errorMsg);
        if (!"0".equals(errorCode)) {
            throw new BussinessException(errorMsg);
        }
    }
    /**
     * 提交代付
     *
     * @return
     * @throws Exception
     */
    public static String doSingleDF(Map<String, String> datas) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("recAcct", datas.get("bankNo"));// 收款的银行账户
        params.put("orderAmt", datas.get("amount"));// 汇款的具体金额
        params.put("accountName", datas.get("realName"));// 收款客户名
        params.put("bankName", datas.get("bankName"));// 收款银行的名称
        params.put("notifyURL", redisService.get(ConfigNID.WEB_SERVER_URL) + "/api/web/notify/dfNotify");
        params.put("merFlowNo", datas.get("orderNo"));// 商户流水号
        params.put("userIp", datas.get("ip"));// 访问ip地址

        String sourceData = getSourceDataForDF(params);

        String base64Data = new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK")));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("interfaceName", INTERFACE_DAIFU);
        paramMap.put("version", VERSION);
        paramMap.put("tranData", base64Data);
        paramMap.put("merSignMsg", ProcessMessage.signMessage(sourceData, PFX_PATH, PASSWORD));
        paramMap.put("merchantId", MERCHANTID);

        String result = HttpUtil.post(URL_DAIFU, paramMap);
        JSONObject jo = JSONUtil.parseObj(result);
        String errorCode = jo.get("errorCode").toString();
        String errorMsg = jo.get("errorMsg").toString();
        String tranDataBase64 = jo.get("tranData").toString();
        String merSignMsg = jo.get("merSignMsg").toString();

        String tranDataGBK = new String(ProcessMessage.Base64Decode(tranDataBase64), "GBK");//通知base64解密用GBK

        log.info("代付申请应答码：{}，应答结果：{}，业务受理结果：{}", errorCode, errorMsg, tranDataGBK);

        if (!"0".equals(errorCode)) {
            return errorMsg;
        }
        if (!YYPayUtil.verifyMessage(tranDataGBK, merSignMsg)) {
            return "代付应答信息验签失败";
        }
        if (!"0000".equals(getNodeValue(tranDataGBK, "returnCode"))) {
            return "代付申请受理失败";
        }
        return null;
    }


    /**
     * 调用支付页面
     *
     * @param datas
     * @return
     */
    public static String getPayPageHtml(Map<String, String> datas) throws Exception {

        List<PayChannel> channels = redisService.getList(CacheID.YYPAY_CHANNEL, PayChannel.class);
        if (CollectionUtils.isEmpty(channels)) {
            channels = getBanksForPay();
            if (CollectionUtils.isEmpty(channels)) {
                throw new BussinessException("无可用支付通道");
            }
        }
        PayChannel channel = channels.get(0);

        Map<String, String> params = new HashMap<>();
        params.put("orderNo", datas.get("orderNo"));
        params.put("orderAmt", datas.get("amount"));
        params.put("curType", "CNY");
        params.put("bankId", channel.getBankID());
        params.put("returnURL", redisService.get(ConfigNID.WEB_SERVER_URL) + "/result?tk=" + datas.get("orderNo"));
        params.put("notifyURL", redisService.get(ConfigNID.WEB_SERVER_URL) + "/api/web/notify/payNotify");
        params.put("userId", datas.get("userId"));
        params.put("goodsType", "1");// 0实体商品 1虚拟商品
        params.put("isBind", "1"); // 0不送银行卡 1送银行卡
        params.put("mobile", datas.get("mobile"));
        params.put("certNo", datas.get("idCard"));
        params.put("userName", datas.get("realName"));
        params.put("cardNo", datas.get("bankNo"));
        params.put("cardType", channel.getCardType()); // 01借记卡 02信用卡 X借记/信用卡都支持
        params.put("bankType", getBankCode(datas.get("bankCode")));// 银行代码
        params.put("returnFlag", "0");// 0返回页面，1返回字符串
        params.put("merchantId", MERCHANTID);// 商户号
        String sourceData = getSourceDataForPay(params);

        params.clear();
        params.put("interfaceName", INTERFACE_PAYORDER);
        params.put("version", VERSION);
        params.put("tranData", new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK"))));
        params.put("merSignMsg", ProcessMessage.signMessage(sourceData, PFX_PATH, PASSWORD));
        params.put("merchantId", MERCHANTID);
        params.put("url", URL_PAYORDER);

        return getPayHtml(params);
    }

    /**
     * 验证签名
     *
     * @param data
     * @param sign
     * @return
     */
    public static boolean verifyMessage(String data, String sign) {
        String verify = ProcessMessage.verifyMessage(data, sign, CERT_PATH);
        log.info("CERT验签结果：{}", verify);
        if (verify.equals("0")) {
            return true;
        }
        return false;
    }

    public static String getPayPageHtmlDemo(String amount) throws Exception {

	/*
		支付测试卡号：
		仅在测试环境使用  测试卡（银联全渠道）：cardType=X，bankId=888C

		银联测试卡号==》是走的银联测试通道
		平安银行借记卡：6216261000000000018
		手机号：13552535506
		证件类型：01
		证件号：341126197709218366
		密码：123456
		姓名：全渠道
		短信验证码：123456（手机）/111111（PC）（先点获取验证码之后再输入）
	 */
        Map<String, String> params = new HashMap<>();
        String orderNo = OrderNoUtils.getSerialNumber();
        params.put("orderNo", orderNo);
        params.put("orderAmt", amount);
        params.put("curType", "CNY");
        params.put("bankId", "888C");
        params.put("returnURL", redisService.get(ConfigNID.WEB_SERVER_URL) + "/result?tk=" + orderNo);
        params.put("notifyURL", redisService.get(ConfigNID.WEB_SERVER_URL) + "/api/web/notify/payNotify");
        params.put("userId", "1");
        params.put("goodsType", "1");// 0实体商品 1虚拟商品
        params.put("isBind", "1"); // 0不送银行卡 1送银行卡
        params.put("mobile", "13552535506");
        params.put("certNo", "341126197709218366");
        params.put("userName", "全渠道");
        params.put("cardNo", "6216261000000000018");
        params.put("cardType", "X"); // 01借记卡 02信用卡 X借记/信用卡都支持
        params.put("bankType", "307");// 银行代码
        params.put("returnFlag", "0");// 0返回页面，1返回字符串
        params.put("merchantId", "M100001563");// 商户号
        String sourceData = getSourceDataForPay(params);

        String sign = ProcessMessage.signMessage(sourceData, "config/yoyiTestNew.pfx", "1q2w3e4r");
        params.clear();
        params.put("interfaceName", INTERFACE_PAYORDER);
        params.put("version", VERSION);
        params.put("tranData", new BASE64Encoder().encode(sourceData.getBytes(Charset.forName("GBK"))));
        params.put("merSignMsg", sign);
        params.put("merchantId", "M100001563");
        params.put("url", "http://testpay.yoyipay.com:28080/pay/anonymousPayOrder.do");

        return getPayHtml(params);
    }


    public static String getErrorPageHtml(String orderNo) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" />\n" +
                "<title>支付结果</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "支付页面跳转中...\n" +
                "<form style = \"display:none;\" method=\"post\" action='" + redisService.get(ConfigNID.WEB_SERVER_URL) + "/result?tk=" + orderNo + "'>\n" +
                "</form>\n" +
                "<script>document.forms[0].submit();</script>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String getPayHtml(Map<String, String> params) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" />\n" +
                "<title>快捷支付</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "支付页面跳转中...\n" +
                "<form style = \"display:none;\" method=\"post\" action='" +  params.get("url") + "'>\n" +
                "<input type=\"hidden\" name=\"interfaceName\" value='" + params.get("interfaceName") + "' />\n" +
                "<input type=\"hidden\" name=\"version\" value='" + params.get("version") + "' />\n" +
                "<input type=\"hidden\" name=\"tranData\" value='" + params.get("tranData") + "' />\n" +
                "<input type=\"hidden\" name=\"merSignMsg\" value='" + params.get("merSignMsg") + "' />\n" +
                "<input type=\"hidden\" name=\"merchantId\" value='" + params.get("merchantId") + "' />\n" +
                "</form>\n" +
                "<script>document.forms[0].submit();</script>\n" +
                "</body>\n" +
                "</html>";

        log.info("支付页面:" + html);
        return html;
    }

    public static String getSourceDataForTH(Map<String, String> params) {
        return "<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>" +
                "<payType>" + params.get("payType") + "</payType><notifyURL>" + params.get("notifyURL") + "</notifyURL></B2CReq>";
    }

    public static String getSourceDataForDF(Map<String, String> params) {
        return "<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>" +
                "<recAcct>" + params.get("recAcct") + "</recAcct><orderAmt>" + params.get("orderAmt") + "</orderAmt><accountName>" + params.get("accountName") + "</accountName>" +
                "<bankName>" + params.get("bankName") + "</bankName><sonName></sonName><tranFlowNo></tranFlowNo><remark></remark><userType>1</userType><bankflag>1</bankflag>" +
                "<recAcctBankNo></recAcctBankNo><merFlowNo>" + params.get("merFlowNo") + "</merFlowNo><userIp>" + params.get("userIp") + "</userIp><cardType>0</cardType>" +
                "<notifyURL>" + params.get("notifyURL") + "</notifyURL><ticketNo></ticketNo><customerId></customerId></B2CReq>";
    }

    public static String getSourceDataForPay(Map<String, String> params) {
        return "<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq>" +
                "<merchantId>" + params.get("merchantId") + "</merchantId><orderNo>" + params.get("orderNo") + "</orderNo><orderAmt>" + params.get("orderAmt") + "</orderAmt>" +
                "<curType>" + params.get("curType") + "</curType><bankId>" + params.get("bankId") + "</bankId><returnURL>" + params.get("returnURL") + "</returnURL>" +
                "<notifyURL>" + params.get("notifyURL") + "</notifyURL><remark></remark><userId>" + params.get("userId") + "</userId>" +
                "<goodsType>" + params.get("goodsType") + "</goodsType><isBind>" + params.get("isBind") + "</isBind><mobile>" + params.get("mobile") + "</mobile>" +
                "<certNo>" + params.get("certNo") + "</certNo><userName>" + params.get("userName") + "</userName><cardNo>" + params.get("cardNo") + "</cardNo>" +
                "<bankType>" + params.get("bankType") + "</bankType><cardType>" + params.get("cardType") + "</cardType><goodsName></goodsName>" +
                "<MSMerchantIdB></MSMerchantIdB><returnFlag>" + params.get("returnFlag") + "</returnFlag></B2CReq>";
    }


    /**
     * 解析node值
     *
     * @param xmlStr
     * @param nodeName
     * @return
     */
    public static String getNodeValue(String xmlStr, String nodeName) {
        String start = "<" + nodeName + ">";
        String end = "</" + nodeName + ">";
        if (StrUtil.isBlank(xmlStr) || !xmlStr.contains(start) || !xmlStr.contains(end)) {
            return null;
        }
        return xmlStr.split(start)[1].split(end)[0];
    }

    /**
     * 银行编码对照
     *
     * @param code
     * @return
     */
    public static String getBankCode(String code) {

        switch (code) {
            case "B0001":
                return "102";//	中国工商银行
            case "B0002":
                return "103";//	中国农业银行
            case "B0003":
                return "104";//	中国银行
            case "B0004":
                return "105";//	中国建设银行
            case "B0005":
                return "203";//	中国农业发展银行
            case "B0006":
                return "301";//	交通银行
            case "B0007":
                return "302";//	中信银行
            case "B0008":
                return "303";//	中国光大银行
            case "B0009":
                return "304";//	华夏银行
            case "B0010":
                return "305";//	中国民生银行
            case "B0011":
                return "306";//	广发银行
            case "B0012":
                return "307";//	平安银行
            case "B0013":
                return "308";//	招商银行
            case "B0014":
                return "309";//	北京银行
            case "B0015":
                return "310";//	上海浦东发展银行
            case "B0016":
                return "311";//	大连银行
            case "B0017":
                return "312";//	上海银行
            case "B0018":
                return "313";//	广州农村商业银行
            case "B0019":
                return "315";//	恒丰银行
            case "B0020":
                return "316";//	浙商银行
            case "B0021":
                return "318";//	渤海银行
            case "B0022":
                return "319";//	徽商银行
            case "B0023":
                return "320";//	宁波银行
            case "B0024":
                return "321";//	杭州银行
            case "B0025":
                return "322";//	江苏银行
            case "B0026":
                return "323";//	兰州银行
            case "B0027":
                return "324";//	南昌银行
            case "B0028":
                return "325";//	南京银行
            case "B0029":
                return "326";//	齐鲁银行
            case "B0030":
                return "327";//	齐商银行
            case "B0031":
                return "328";//	天津银行
            case "B0032":
                return "329";//	温州银行
            case "B0033":
                return "330";//	西安银行
            case "B0034":
                return "331";//	厦门银行
            case "B0035":
                return "332";//	鄞州银行
            case "B0036":
                return "335";//	台州银行
            case "B0037":
                return "336";//	广州银行
            case "B0038":
                return "342";//	临商银行
            case "B0039":
                return "345";//	嘉兴银行
            case "B0040":
                return "346";//	金华银行
            case "B0041":
                return "348";//	青岛银行
            case "B0042":
                return "349";//	南阳银行
            case "B0043":
                return "403";//	中国邮政储蓄银行
            case "B0044":
                return "504";//	恒生银行
            case "B0045":
                return "642";//	成都银行
            case "B0046":
                return "807";//	深圳发展银行
            case "B0047":
                return "809";//	兴业银行
            default:
                return null;
        }
    }
}
