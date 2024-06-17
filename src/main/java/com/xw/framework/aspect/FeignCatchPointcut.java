package com.xw.framework.aspect;

import com.xw.framework.annotation.FeignCatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Created by MaxWell on 2024/6/17 22:33
 */
@Slf4j
@Aspect
@Component
@Lazy(false)
@Order(99)
public class FeignCatchPointcut {

    @Pointcut("within(@com.xw.framework.annotation.FeignCatch *)")
    private void cutMethod() {
    }

    @Around(value = "cutMethod()")
    public Object doCatch(ProceedingJoinPoint joinPoint) {
        // 执行源方法
        Object instance = null;
        try {
            FeignCatch feignCatch = getFeignCatchAnnotation(joinPoint);
            if (feignCatch != null) {
                // 实例化类对象
                instance = feignCatch.clazz().getDeclaredConstructor().newInstance();
                // 反射设置字段值
                setFieldValue(instance, feignCatch.errorCodeField(), feignCatch.errorCode());
                setFieldValue(instance, feignCatch.errorMsgField(), feignCatch.errorMsg());
            }
            return joinPoint.proceed();
        } catch (Throwable e) { // 获取FeignCatch注解
            log.error("第三方feign接口调用异常", e);
            return instance;
        }
    }

    private FeignCatch getFeignCatchAnnotation(ProceedingJoinPoint joinPoint) {
        // 获取JoinPoint的签名，这通常是被代理的方法
        Signature signature = joinPoint.getSignature();
        // 如果签名是MethodSignature，我们可以通过它获取到实际的方法
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            // 获取方法所在的类
            Class<?> targetClass = methodSignature.getDeclaringType();
            // 尝试获取FeignCatch注解
            return targetClass.getAnnotation(FeignCatch.class);
        }
        return null;
    }

    private void setFieldValue(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("设置字段值失败", e);
        }
    }
}
