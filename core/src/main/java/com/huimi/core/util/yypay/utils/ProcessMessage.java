//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.huimi.core.util.yypay.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ProcessMessage {
    public static PrivateKey privateKey = null;
    public static PublicKey publicKey = null;
    public static String signDate = null;

    public ProcessMessage() {
    }

    public static String signMessage(String srcMessage, String certPath, String password) throws Exception {
        String b64SignMsg = null;

        try {
            String date = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
            if (privateKey == null || !date.equals(signDate)) {
                privateKey = CertSignUtil.getRSACertPrivateKey(certPath, password, "PKCS12");
                signDate = date;
            }

            b64SignMsg = (new BASE64Encoder()).encode(CertSignUtil.digitalSign(srcMessage.getBytes("GBK"), privateKey, "SHA1withRSA"));
        } catch (CertificateExpiredException var5) {
            var5.printStackTrace();
            return "1";
        } catch (CertificateNotYetValidException var6) {
            var6.printStackTrace();
            return "2";
        } catch (Exception var7) {
            var7.printStackTrace();
            return "0";
        }
        String sign = b64SignMsg.replaceAll("\r", "").replaceAll("\n", "");
        log.info("支付签名原串：" + srcMessage + "\n甬易支付签名：" + sign);
        return sign;
    }

    public static String verifyMessage(String srcMessage, String resMessage, String certPath) {
        try {
            String date = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
            if (publicKey == null || !date.equals(signDate)) {
                publicKey = CertSignUtil.getRSAPublicKeyByFile(certPath);
                signDate = date;
            }

            boolean verify = CertSignUtil.verifyDigitalSign(srcMessage.getBytes("GBK"), (new BASE64Decoder()).decodeBuffer(resMessage), publicKey, "SHA1withRSA");
            return !verify ? "1" : "0";
        } catch (CertificateExpiredException var5) {
            return "2";
        } catch (CertificateNotYetValidException var6) {
            return "3";
        } catch (Exception var7) {
            var7.printStackTrace();
            return "4";
        }
    }

    public static String verifyMessage(String srcMessage, String resMessage, byte[] bCert) {
        try {
            String date = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
            if (publicKey == null || !date.equals(signDate)) {
                publicKey = CertSignUtil.getRSAPublicKeyByString(new String(bCert));
                signDate = date;
            }

            boolean verify = CertSignUtil.verifyDigitalSign(srcMessage.getBytes("GBK"), (new BASE64Decoder()).decodeBuffer(resMessage), publicKey, "SHA1withRSA");
            return !verify ? "1" : "0";
        } catch (CertificateExpiredException var5) {
            return "2";
        } catch (CertificateNotYetValidException var6) {
            return "3";
        } catch (Exception var7) {
            var7.printStackTrace();
            return "4";
        }
    }

    public static String Base64Encode(byte[] content) {
        return (new BASE64Encoder()).encode(content).replaceAll("\r", "").replaceAll("\n", "");
    }

    public static byte[] Base64Decode(String content) {
        byte[] decData = (byte[]) null;

        try {
            decData = (new BASE64Decoder()).decodeBuffer(content);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return decData;
    }
}
