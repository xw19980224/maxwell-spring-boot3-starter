package com.xw.framework.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xw.framework.config.JwtConfig;
import com.xw.framework.domain.CommonResult;
import com.xw.framework.domain.ResultCode;
import com.xw.framework.utils.bean.JwtTool;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by MaxWell on 2023/12/11 10:54
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "maxwell.auth-jwt", havingValue = "true")
@Order(999)
public class AuthFilter extends BaseFilter implements Filter {

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (writeList(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(jwtConfig.getHeaderName());
        if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            JwtTool.JwtVerifyResult jwtVerifyResult = JwtTool.claimsToken(token,
                    jwtConfig.getKey(), jwtConfig.getSecret(), jwtConfig.getLeewaySeconds());

            if (jwtVerifyResult.getSuccess()) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        CommonResult<Object> failed = CommonResult.failed(ResultCode.UNAUTHORIZED);
        String result = JSONUtil.toJsonStr(failed);
        response.setContentType("text/plain;charset=UTF-8");
        response.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
    }
}
