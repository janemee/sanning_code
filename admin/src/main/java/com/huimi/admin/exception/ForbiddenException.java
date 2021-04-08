package com.huimi.admin.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author zhangfoshou
 * @date 2018/10/20 16:02
 */
public class ForbiddenException extends AuthenticationException {
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }
}

