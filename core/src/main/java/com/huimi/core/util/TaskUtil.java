package com.huimi.core.util;

import cn.hutool.http.HtmlUtil;
import com.huimi.common.tools.StringUtil;
import com.huimi.common.utils.ValidateUtils;
import com.huimi.core.common.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskUtil {
    private static final Logger log = LoggerFactory.getLogger(TaskUtil.class);

    /**
     * 解析抖音直播间链接
     *
     * @param ss
     * @return
     */
    public static String saveLiveInContent(String ss) {
        try {
            long s1 = System.currentTimeMillis();
            StringBuilder stringBuffer = new StringBuilder();
            //String ss = "#在抖音，记录美好生活#【俊姐 情感主播】正在直播，来和我一起支持TA吧。复制下方链接，打开【抖音短视频】，直接观看直播！ https://v.douyin.com/J617CGY/";
            //获取直播间地址
            String detailUrl = StringUtil.subTextToWebUrlStr(ss);
            //获取直播间详情地址
            String dataStr = HttpUtils.POST_FORM(detailUrl, null);
            if (StringUtil.isBlank(dataStr)) {
                return "";
            }
            //请求直播间详情地址
            String detailData = HttpUtils.sendGet(dataStr, "");
            if (StringUtil.isBlank(detailData)) {
                return "";
            }
            //清楚tag标签的str
            String cleanHtmlTag = HtmlUtil.cleanHtmlTag(detailData);
            //获取第一个short_id的位置
            int start = cleanHtmlTag.indexOf("short_id\":");
            //计算结束截取的位置
            int end = start + 20;
            String shortId = cleanHtmlTag.substring(start + 10, end);
            if (shortId.indexOf(",") > 0) {
                shortId = shortId.substring(0, shortId.indexOf(","));
            }
            //user_id 必须是数字类型 如果转换不成功 那么表示链接有问题
            if (!ValidateUtils.isNumeric(shortId)) {
                log.error("shortId：" + shortId + "         获取直播间的userId有误");
                return "";
            }
            //获取房间号 （在详情地址的后缀上）
            String roomId = dataStr.substring(dataStr.lastIndexOf("/") + 1, dataStr.indexOf("?"));
            //需要的数据格式
            //String ss = "snssdk1128://live?room_id=6857498076832074511&user_id=68620688339&from=webview&refer=web&_t=1596638088108  ";
            stringBuffer.append("snssdk1128://live");
            // 房间号
            stringBuffer.append("?room_id=").append(roomId);
            //用户号
            stringBuffer.append("&user_id=").append(shortId);
            //来源
            stringBuffer.append("&from=webview");
            //固定为web
            stringBuffer.append("&refer=web");
            //时间戳
            stringBuffer.append("&_t=").append(new Date().getTime());
            System.out.println("解析花费:" + (System.currentTimeMillis() - s1));
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("未找到直播间");
            return "";
        }
    }


    /**
     * 解析快手直播间链接
     *
     * @param linkUrl
     * @return
     */
    public static String saveLiveInContentKwaiFu(String linkUrl) {
        try {
            long s1 = System.currentTimeMillis();
            StringBuilder stringBuffer = new StringBuilder();
            //String ss = "https://v.kuaishou.com/6iPCba";
            //获取直播间地址
            String detailUrl = StringUtil.subTextToWebUrlStr(linkUrl);
            //获取直播间详情地址
            String dataStr = HttpUtils.POST_FORM(detailUrl, null);
            System.out.println("dataStr:" + dataStr);
            if (StringUtil.isBlank(dataStr)) {
                return "";
            }
            //请求直播间详情地址
            String detailData = HttpUtils.sendGet(dataStr, "");
            if (StringUtil.isBlank(detailData)) {
                return "";
            }
            System.out.println("detailData:" + detailData);
            //前端需要的直播间地址 固定路径+ shareObjectId
            String headStr = "kwai://live/play/";
            //获取第一个short_id的位置
            int start = detailData.indexOf(headStr);
            //计算结束截取的位置
            int end = start + headStr.length() + 11;
            String headTitleStr = detailData.substring(start, end);
            if (StringUtil.isBlank(headTitleStr)) {
                return "";
            }
            //需要的数据格式
//            String prodUrl = "kwai://live/play/YJFLFdi9FSI?shareId=562714277334&shareToken=X8SjoKCySMgG1cC_A";
            stringBuffer.append(headTitleStr);
            // 主播id + token
            stringBuffer.append(checkedKwaiFu(dataStr));
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("未找到直播间");
            return "";
        }
    }

    public static void main(String[] args) {
        String ss = "#在抖音，记录美好生活#【俊姐 情感主播】正在直播，来和我一起支持TA吧。复制下方链接，打开【抖音短视频】，直接观看直播！ https://v.douyin.com/J685dWE/";
        String s1 = "报错的直播间链接：https://v.douyin.com/Jfs8vcF/";
        String s2 = " https://v.douyin.com/JP5Ampb/";
        String s3 = "https://v.kuaishou.com/9iOOaK/";
        String ll = "https://c.kuaishou.com/fw/live/ning0816930?fid=2139846899&cc=share_copylink&followRefer=151&shareMethod=TOKEN&docId=5&kpn=KUAISHOU&subBiz=LIVE_STREAM&shareId=562714277334&shareToken=X8SjoKCySMgG1cC_A&shareResourceType=LIVESTREAM_OTHER&userId=2050580744&shareType=5&et=1_a%2F2000137523093864593_lsssr0%24s&shareMode=APP&originShareId=562714277334&appType=21&shareObjectId=YJFLFdi9FSI&shareUrlOpened=0&timestamp=1605415825418";
//        saveLiveInContent(s1);
//        checkedKwaiFu(ll);
//        Map<String,String>  dataMap = new HashMap<>();
//        String lll ="https://c.kuaishou.com/fw/live/g13514388616?fid=122623964&cc=share_copylink&followRefer=151&shareMethod=TOKEN&docId=5&kpn=KUAISHOU&subBiz=LIVE_STREAM&shareId=1110122506998&shareToken=X-1V9lnJkjjU95ix_A&shareResourceType=LIVESTREAM_OTHER&userId=1267828967&shareType=5&et=1_a%2F2000160291040449777_lsssr101&shareMode=APP&originShareId=1110122506998&appType=21&shareObjectId=_t6xInRh1HI&shareUrlOpened=0&timestamp=1608433033089";
//        String[] strings = lll.split("&");
//        for (String s:strings){
//            if((s.indexOf("userId="))!=-1){
//                dataMap.put("userId",s);
//            }
//            if((s.indexOf("shareId="))!=-1){
//
//                dataMap.put("shareId",s);
//            }
//            if((s.indexOf("shareToken="))!=-1){
//
//                dataMap.put("shareToken",s);
//            }
//            System.out.println(s);
//        }
//        System.out.println(dataMap);
        System.out.println(saveLiveInContentKwaiFu(s3));
    }

    public static String checkedKwaiFu(String linkUrl) {

//        String LLL = "https://c.kuaishou.com/fw/live/ning0816930?fid=2139846899&cc=share_copylink&followRefer=151&shareMethod=TOKEN&docId=5&kpn=KUAISHOU&subBiz=LIVE_STREAM&shareId=562714277334&shareToken=X8SjoKCySMgG1cC_A&shareResourceType=LIVESTREAM_OTHER&userId=2050580744&shareType=5&et=1_a%2F2000137523093864593_lsssr0%24s&shareMode=APP&originShareId=562714277334&appType=21&shareObjectId=YJFLFdi9FSI&shareUrlOpened=0&timestamp=1605415825418";
        //获取 shareId
//        String content = linkUrl.substring(linkUrl.indexOf("shareId"));
//        String shareId = content.substring(content.lastIndexOf("shareId="), "shareId=".length() + 12);
//        //获取 shareToken
//        String shareTokenContent = linkUrl.substring(linkUrl.indexOf("shareToken="));
//        String shareToken = shareTokenContent.substring(shareTokenContent.indexOf("shareToken="), "shareToken=".length() + 17);
//        //拼装返回数据
//        return "?" + shareId + "&" + shareToken;
        Map<String,String>  dataMap = new HashMap<>();
        String[] strings = linkUrl.split("&");
        for (String s:strings){
            if((s.indexOf("userId="))!=-1){
                dataMap.put("userId",s);
            }
            if((s.indexOf("shareId="))!=-1){

                dataMap.put("shareId",s);
            }
            if((s.indexOf("shareToken="))!=-1){

                dataMap.put("shareToken",s);
            }
            System.out.println(s);
        }
        return "?"+dataMap.get("shareId")+"&"+dataMap.get("shareToken");
    }



}
