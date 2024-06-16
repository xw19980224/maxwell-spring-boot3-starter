package com.xw.framework.filter;


import jakarta.servlet.http.HttpServletRequest;

/**
 * Created by MaxWell on 2023/12/7 10:46
 */
public class BaseFilter {

    protected boolean writeList(HttpServletRequest req) {
        return req.getRequestURI().equals("/") ||
                req.getRequestURI().startsWith("/actuator") ||
                req.getRequestURI().startsWith("/v2/api-docs") ||
                req.getRequestURI().startsWith("/webjars") ||
                req.getRequestURI().equals("/swagger-resources") ||
                req.getRequestURI().equals("/doc.html") ||
                req.getRequestURI().equals("/favicon.ico") ||
                req.getRequestURI().startsWith("/v3/api-docs");
    }
}
