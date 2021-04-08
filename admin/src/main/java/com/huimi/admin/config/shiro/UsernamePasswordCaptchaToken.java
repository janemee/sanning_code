package com.huimi.admin.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by dqw on 2015/11/13.
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public UsernamePasswordCaptchaToken() {
        super();
    }

    public UsernamePasswordCaptchaToken(String username, String password, String captcha) {
        super(username, password);
        this.captcha = captcha;
    }
}
