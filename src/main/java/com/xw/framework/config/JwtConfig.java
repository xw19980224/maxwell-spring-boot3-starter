package com.xw.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MaxWell on 2023/12/11 10:12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "maxwell.jwt")
public class JwtConfig {
    private String headerName = "Authorization";
    private String key;
    private String secret;
    private int expireTime = 7200;
    /**
     * = JWT容错时间 (默认120秒)
     */
    private int leewaySeconds = 120;
}
