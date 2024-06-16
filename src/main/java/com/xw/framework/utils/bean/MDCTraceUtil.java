package com.xw.framework.utils.bean;

import cn.hutool.core.lang.UUID;
import org.slf4j.MDC;

/**
 * MDC工具类
 * Created by MaxWell on 2024/6/12 21:59
 */
public class MDCTraceUtil {

    /**
     * 追踪id的名称
     */
    public static final String KEY_TRACE_ID = "traceId";

    /**
     * 日志链路追踪id信息头
     */
    public static final String TRACE_ID_HEADER = "x-traceId-header";


    /**
     * 创建traceId并赋值MDC
     */
    public static void addTrace() {
        String traceId = createTraceId();
        // MDC(Mapped Diagnostic Context)诊断上下文映射，是@Slf4j提供的一个支持动态打印日志信息的工具。
        MDC.put(KEY_TRACE_ID, traceId);
    }

    /**
     * 赋值MDC
     */
    public static void putTrace(String traceId) {
        MDC.put(KEY_TRACE_ID, traceId);
    }

    /**
     * 获取MDC中的traceId值
     */
    public static String getTraceId() {
        return MDC.get(KEY_TRACE_ID);
    }

    /**
     * 清除MDC的值
     */
    public static void removeTrace() {
        MDC.remove(KEY_TRACE_ID);
    }

    /**
     * 创建traceId
     */
    public static String createTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
