package com.xw.framework.domain;

/**
 * API返回码接口
 * Created by MaxWell on 2023/12/7 10:26
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
