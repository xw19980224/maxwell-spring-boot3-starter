package com.xw.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MaxWell on 2023/12/11 11:11
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "maxwell.filter")
public class MaxwellFilterConfig {
    /**
     * 是否返回通用JSON结果
     */
    private boolean result;
    /**
     * 是否启用JWT认证
     */
    private boolean authJwt;

    /**
     * 鉴权过期时间
     */
    private Long authExpireSeconds = 3600L;

    /**
     * 鉴权请求头名称
     */
    private String authHeaderName = "Authorization";
}
