package com.huimi.apis.controller.pay;

import com.huimi.apis.controller.WebGenericController;
import com.huimi.core.constant.CacheID;
import com.huimi.core.service.cache.RedisService;
import com.huimi.core.util.yypay.YYPayUtil;
import com.huimi.core.util.yypay.model.ResultModel;
import com.ruirong.certUtil.ProcessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Controller
@RequestMapping("/api/web/notify")
public class YYPayNotifyController extends WebGenericController {

    @Autowired
    RedisService redisService;


    @PostMapping(value = "/payNotify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String interfaceName = request.getParameter("interfaceName");
        String tranDataBase64 = request.getParameter("tranData");
        String signData = request.getParameter("signData");
        String version = request.getParameter("version");

        String tranDataGBK = new String(ProcessMessage.Base64Decode(tranDataBase64), "GBK");//通知base64解密用GBK
        log.info("支付回调明文结果(GBK)：" + tranDataGBK);

        if (YYPayUtil.verifyMessage(tranDataGBK, signData)) {
            ResultModel result = new ResultModel(t -> {
                t.setType("RECHARGE");
                t.setOrderAmt(YYPayUtil.getNodeValue(tranDataGBK, "orderAmt"));
                t.setOrderNo(YYPayUtil.getNodeValue(tranDataGBK, "orderNo"));
                t.setTranSerialNo(YYPayUtil.getNodeValue(tranDataGBK, "tranSerialNo"));
                t.setTranStat(YYPayUtil.getNodeValue(tranDataGBK, "tranStat"));
            });
            redisService.push(CacheID.QUEUE_RECHARGE, result);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }


    @PostMapping(value = "/dfNotify")
    public void dfNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String interfaceName = request.getParameter("interfaceName");
        String tranDataBase64 = request.getParameter("tranData");
        String signData = request.getParameter("merSignMsg");
        String version = request.getParameter("version");

        String tranDataGBK = new String(ProcessMessage.Base64Decode(tranDataBase64), "GBK");//通知base64解密用GBK
        log.info("代付回调明文结果(GBK)：" + tranDataGBK);

        if (YYPayUtil.verifyMessage(tranDataGBK, signData)) {
            ResultModel result = new ResultModel(t -> {
                t.setType("CASHBACK");
                t.setOrderAmt(YYPayUtil.getNodeValue(tranDataGBK, "transAmt"));
                t.setOrderNo(YYPayUtil.getNodeValue(tranDataGBK, "merFlowNo"));
                t.setTranSerialNo(YYPayUtil.getNodeValue(tranDataGBK, "tranSerialNo"));
                t.setTranStat(YYPayUtil.getNodeValue(tranDataGBK, "status"));
                t.setFullMsg(tranDataGBK);
            });

            redisService.push(CacheID.QUEUE_CASH, result);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }


    /**
     * 退汇异步通知接口
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping(value = "/thNotify")
    public void thNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tranDataBase64 = request.getParameter("tranData");
        String signData = request.getParameter("merSignMsg");

        String tranDataGBK = new String(ProcessMessage.Base64Decode(tranDataBase64), "GBK");//通知base64解密用GBK
        log.info("退汇通知明文结果(GBK)：" + tranDataGBK);

        if (YYPayUtil.verifyMessage(tranDataGBK, signData)) {
            ResultModel result = new ResultModel(t -> {
                t.setType("CASHTUIHUI");
                t.setOrderAmt(YYPayUtil.getNodeValue(tranDataGBK, "orderAmt"));
                t.setOrderNo(YYPayUtil.getNodeValue(tranDataGBK, "orderNo"));
                t.setTranSerialNo(YYPayUtil.getNodeValue(tranDataGBK, "transFlowNo"));
                t.setTranStat(YYPayUtil.getNodeValue(tranDataGBK, "orderState"));
                t.setFullMsg(tranDataGBK);
            });

            redisService.push(CacheID.QUEUE_CASH, result);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }




    /**
     * 退汇异步通知地址推送
     * @throws Exception
     */
    @GetMapping(value = "/submitTuihuiUrl")
    public void submitTuihuiUrl(HttpServletResponse response) throws Exception {
        YYPayUtil.submitTuihuiUrl();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }
    /**
     * 退汇异步通知地址推送
     * @throws Exception
     */
    @GetMapping(value = "/tuihui")
    public void tuihui(HttpServletResponse response) throws Exception {
        YYPayUtil.doTuiHuiDemo();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }

}
