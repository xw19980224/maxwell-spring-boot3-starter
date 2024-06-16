package com.xw.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by MaxWell on 2024/6/5 23:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "maxwell")
public class MaxwellConfig {
}
