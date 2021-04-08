package com.huimi.common.encode;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 盐加密公共方法
 */
public class SaltEncodeUtil {
    /**
     * 盐加密（无盐）
     *
     * @param pwd
     * @return
     */
    public static SaltModel encode(String pwd) {
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        SimpleHash hash = new SimpleHash("md5", pwd, salt2, 2);
        pwd = hash.toHex();
        SaltModel saltModel = new SaltModel();
        saltModel.setPwd(pwd);
        saltModel.setSalt(salt2);
        return saltModel;
    }

    /**
     * 盐加密（有盐）
     *
     * @param pwd
     * @return
     */
    public static SaltModel saltEncode(String pwd, String salt) {
        SimpleHash hash = new SimpleHash("md5", pwd, salt == null ? "" : salt, 2);
        System.out.println(salt);
        pwd = hash.toHex();
        SaltModel saltModel = new SaltModel();
        saltModel.setPwd(pwd);
        return saltModel;
    }

    public static void main(String args[]) {
        String pwd = "aa123123";
        SaltModel saltModel = encode(pwd);
        System.out.println("pwd : " + saltModel.getPwd() + "   salt:" + saltModel.getSalt());

        SaltModel saltModel1 = saltEncode(pwd, saltModel.getSalt());
        System.out.println(saltModel);
    }
}
