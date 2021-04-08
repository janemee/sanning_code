package com.huimi.core.util.weChatPay.util;

import org.apache.commons.io.IOUtils;
import com.huimi.core.util.weChatPay.WeiXinConfig;
import java.io.*;

import static com.huimi.core.util.weChatPay.util.WXPayConstants.DOMAIN_API;


public class MyConfig extends WXPayConfig {

    /**
     * 加载证书  这里证书需要到微信商户平台进行下载
     */
    private byte[] certData;

    public MyConfig() throws Exception {
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return WeiXinConfig.appid;
    }

    @Override
    public String getMchID() {
        return WeiXinConfig.mch_id;
    }

    @Override
    public String getKey() {
        return WeiXinConfig.key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
                new DomainInfo(domain, false);
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(DOMAIN_API, false);
            }
        };
    }
}
