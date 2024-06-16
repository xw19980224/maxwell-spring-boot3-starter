package com.xw.framework.exception;

/**
 * 系统异常
 * Created by MaxWell on 2024/6/9 16:16
 */
public class SysException extends RuntimeException {
    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
