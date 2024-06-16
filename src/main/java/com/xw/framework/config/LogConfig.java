package com.xw.framework.config;

import cn.hutool.json.JSONObject;
import com.xw.framework.filter.BaseFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 日志配置
 * Created by MaxWell on 2023/12/7 14:04
 */
@Configuration
public class LogConfig extends BaseFilter {

    private static final Logger log = LoggerFactory.getLogger(LogConfig.class);

    @Bean
    public OncePerRequestFilter contentCachingRequestFilter() {
        // 配置一个Filter
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
                if (writeList(request)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // 包装HttpServletRequest，把输入流缓存下来
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
                // 包装HttpServletResponse，把输出流缓存下来
                ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
                filterChain.doFilter(wrappedRequest, wrappedResponse);
                log.info("[{}] {} Params:{} Body:{} |====> ({}) `{}`",
                        wrappedRequest.getMethod(),
                        wrappedRequest.getRequestURI(),
                        getRequestParams(wrappedRequest),
                        new String(wrappedRequest.getContentAsByteArray()).replaceAll(" ", "").replaceAll("[\r\n]", ""),
                        wrappedResponse.getStatus(),
                        new String(wrappedResponse.getContentAsByteArray()).replaceAll(" ", "").replaceAll("[\r\n]", ""));

                // 注意这一行代码一定要调用，不然无法返回响应体
                wrappedResponse.copyBodyToResponse();
            }
        };
    }

    private String getRequestParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            jsonObject.set(key, value);
        }
        return jsonObject.toString();
    }
}
