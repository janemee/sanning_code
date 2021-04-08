//package com.huimi.core.util.aliPay;
//
//import java.beans.Visibility;
//
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.demo.trade.model.ExtendParams;
//import com.alipay.demo.trade.model.GoodsDetail;
//import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
//import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
//import com.alipay.demo.trade.service.AlipayTradeService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.math.BigDecimal;
//import java.util.*;
//
//public class AliPayUtil {
//
//    // 支付宝当面付2.0服务
//    private static AlipayTradeService tradeService;
//
//    public static String doPay() {
//        String msg = "";
//        //todo 向支付宝发送请求业务
//
//        //1 准备请求相关参数 配置信息
//
//
//        return msg;
//    }
//
//    // 测试当面付2.0支付
//    public static String test_trade_pay(BigDecimal amount) {
//        String msg = "";
//        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
//        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
//        String outTradeNo = "tradepay" + System.currentTimeMillis()
//                + (long) (Math.random() * 10000000L);
//
//        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
//        String subject = "xxx品牌xxx门店当面付消费";
//
//        // (必填) 订单总金额，单位为元，不能超过1亿元
//        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
//        String totalAmount = amount.toString();
//
//        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
//        String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
//        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
//        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
//        //        String discountableAmount = "1.00"; //
//
//        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
//        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//        String undiscountableAmount = "0.0";
//
//        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
//        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//        String sellerId = "";
//
//        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
//        String body = "购买商品3件共20.00元";
//
//        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
//        String operatorId = "test_operator_id";
//
//        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
//        String storeId = "test_store_id";
//
//        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//        String providerId = "2088100200300400500";
//        ExtendParams extendParams = new ExtendParams();
//        extendParams.setSysServiceProviderId(providerId);
//
//        // 支付超时，线下扫码交易定义为5分钟
//        String timeoutExpress = "5m";
//
//        // 商品明细列表，需填写购买商品详细信息，
//        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//
//        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);
//
//        String appAuthToken = "应用授权令牌";//根据真实值填写
//
//        // 创建条码支付请求builder，设置请求参数
//        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
//                //            .setAppAuthToken(appAuthToken)
//                .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
//                .setTotalAmount(totalAmount).setStoreId(storeId)
//                .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
//                .setExtendParams(extendParams).setSellerId(sellerId)
//                .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);
//
//        // 调用tradePay方法获取当面付应答
//        AlipayF2FPayResult result = tradeService.tradePay(builder);
//        switch (result.getTradeStatus()) {
//            case SUCCESS:
//                msg = ("支付宝支付成功: )");
//                break;
//
//            case FAILED:
//                msg = ("支付宝支付失败!!!");
//                break;
//
//            case UNKNOWN:
//                msg = ("系统异常，订单状态未知!!!");
//                break;
//
//            default:
//                msg = ("不支持的交易状态，交易返回异常!!!");
//                break;
//        }
//        return msg;
//    }
//
//
//    /**
//     * 支付宝回调验签
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    public static void Visibility(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Map<String, String> params = new HashMap<String, String>();
//        Map<String, String[]> requestParams = request.getParameterMap();
//        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//            }
//            // 乱码解决，这段代码在出现乱码时使用
//            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            params.put(name, valueStr);
//        }
//
//        System.out.println(params);
//        boolean signVerified = AlipaySignature.rsaCheckV1(params, APPAliPayConfig.alipay_public_key, APPAliPayConfig.charset, APPAliPayConfig.sign_type); // 调用SDK验证签名
//
//        // ——请在这里编写您的程序（以下代码仅作参考）——
//
//        /*
//         * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
//         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
//         * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
//         * 4、验证app_id是否为该商户本身。
//         */
//        if (signVerified) {// 验证成功
//            // 商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            // 支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            // 交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
//
//            // 交易金额
//            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
//            System.out.println("商户订单号=" + out_trade_no);
//            System.out.println("支付宝交易号=" + trade_no);
//            System.out.println("交易状态=" + trade_status);
//            // finished 交易成功不可退款, success 交易成功可退款
//            if (trade_status.equals("TRADE_FINISHED")) {
//                // 判断该笔订单是否在商户网站中已经做过处理
//                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                // 如果有做过处理，不执行商户的业务程序
////                doSuccess(out_trade_no, trade_no, trade_status, total_amount, params.toString());
//                // 注意：
//                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//            } else if (trade_status.equals("TRADE_SUCCESS")) {
//                // 判断该笔订单是否在商户网站中已经做过处理
//                // 如果没有做过 处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                // 如果有做过处理，不执行商户的业务程序
////                doSuccess(out_trade_no, trade_no, trade_status, total_amount, params.toString());
//                // 注意：
//                // 付款完成后，支付宝系统发送该交易状态通知
//            }
//
//            System.out.println("异步回调验证成功");
//            response.getWriter().write("success");
//
//        } else {// 验证失败
//            System.out.println("异步回调验证失败");
//            response.getWriter().write("fail");
//
//            // 调试用，写文本函数记录程序运行情况是否正常
//            // String sWord = AlipaySignature.getSignCheckContentV1(params);
//            // AlipayConfig.logResult(sWord);
//        }
//    }
//
//}
