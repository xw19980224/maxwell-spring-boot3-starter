package com.xw.framework.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xw.framework.annotation.RawResponse;
import com.xw.framework.config.MaxwellFilterConfig;
import com.xw.framework.domain.CommonResult;
import com.xw.framework.domain.ResultCode;
import com.xw.framework.filter.BaseFilter;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

/**
 * 全局异常处理及返回值的统一封装
 * Created by MaxWell on 2023/12/7 10:30
 */
@Order(100)
@RestControllerAdvice
public class ResponseAdvice extends BaseFilter implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(ResponseAdvice.class);

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private MaxwellFilterConfig maxwellFilterConfig;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!maxwellFilterConfig.isResult()) {
            return false;
        }
        if (returnType.getDeclaringClass().getName().contains("springdoc")) {
            return false;
        }
        if (returnType.getContainingClass().isAnnotationPresent(RawResponse.class)) {
            return false;
        } else {
            return !Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(RawResponse.class);
        }
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<?
            extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof CommonResult) {
            return body;
        }
        if (body instanceof String) {
            return objectMapper.writeValueAsString(CommonResult.success(body));
        }
        return CommonResult.success(body);
    }

    /**
     * 404异常处理<br>
     *
     * @param e 404异常
     * @return CommonResult
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoResourceFoundException.class)
    public Object noResourceFoundException(NoResourceFoundException e) {
        String message = e.getMessage();
        log.error("404 not found:{}", message, e);
        return CommonResult.<String>failed(ResultCode.NOT_FOUND, String.format(ResultCode.NOT_FOUND.getMessage(), message));
    }

    /**
     * 系统内部异常捕获
     *
     * @param e 异常
     * @return CommonResult
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return CommonResult.failed(ResultCode.FAILED);
    }


    /**
     * 忽略参数异常处理器;触发例子:带有@RequestParam注解的参数未给参数
     *
     * @param e 忽略参数异常
     * @return CommonResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("请求参数异常", e);
        return CommonResult.validateFailed();
    }

    /**
     * 缺少请求体异常处理器;触发例子:不给请求体参数
     *
     * @param e 缺少请求体异常
     * @return CommonResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error("参数请求体异常", e);
        return CommonResult.validateFailed();
    }


    /**
     * 统一处理请求参数绑定错误(实体对象传参);
     *
     * @param e BindException
     * @return CommonResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Object validExceptionHandler(BindException e) {
        log.error("方法参数绑定错误(实体对象传参)", e);
        return CommonResult.validateFailed();
    }

    /**
     * 统一处理请求参数绑定错误(实体对象请求体传参);
     *
     * @param e 参数验证异常
     * @return CommonResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error("方法参数无效异常(实体对象请求体传参)", e);
        return CommonResult.validateFailed();
    }
}
