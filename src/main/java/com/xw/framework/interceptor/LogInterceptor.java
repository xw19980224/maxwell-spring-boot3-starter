package com.xw.framework.interceptor;

import com.xw.framework.utils.bean.MDCTraceUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 日志拦截器
 * Created by MaxWell on 2024/6/12 14:40
 */
public class LogInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID = "TRACE_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //可以考虑让客户端传入链路ID，但需保证一定的复杂度唯一性；如果没使用默认UUID自动生成
        String traceId = request.getHeader(MDCTraceUtil.TRACE_ID_HEADER);
        if (!StringUtils.isEmpty(traceId)) {
            MDCTraceUtil.putTrace(request.getHeader(MDCTraceUtil.TRACE_ID_HEADER));
        } else {
            MDCTraceUtil.addTrace();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        MDCTraceUtil.removeTrace();
    }
}
