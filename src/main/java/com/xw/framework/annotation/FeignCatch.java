package com.xw.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Feign异常捕获注释
 * Created by MaxWell on 2024/6/17 22:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignCatch {
    String errorCodeField() default "code"; // 默认错误字段名

    long errorCode() default 500; // 默认错误码

    String errorMsgField() default "message"; // 默认错误消息字段名

    String errorMsg() default "第三方接口调用异常"; // 默认错误消息

    Class<?> clazz(); // 需要实例化的类
}
