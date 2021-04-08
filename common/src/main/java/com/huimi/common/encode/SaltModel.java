package com.huimi.common.encode;

/**
 * 加密
 */
public class SaltModel {
    String pwd;
    String salt;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
