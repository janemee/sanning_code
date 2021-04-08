package com.huimi.admin.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 没有权限
 *
 * @author zhangfoshou
 * @date 2018/10/11 16:58
 */
public class UnAuthorityException extends AuthenticationException {
    public UnAuthorityException() {
        super();
    }

    public UnAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorityException(String message) {
        super(message);
    }

    public UnAuthorityException(Throwable cause) {
        super(cause);
    }
}
