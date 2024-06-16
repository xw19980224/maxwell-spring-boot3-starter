package com.xw.framework.exception;

/**
 * 业务异常
 * Created by MaxWell on 2024/6/9 16:16
 */
public class BizException extends SysException {
    public Integer code = 500;

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, String message) {
        super(message);
        this.code = Integer.valueOf(code);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BizException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
}
