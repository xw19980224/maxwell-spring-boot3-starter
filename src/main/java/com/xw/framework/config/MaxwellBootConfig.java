package com.xw.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by MaxWell on 2024/6/5 21:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "maxwell.boot")
public class MaxwellBootConfig {

    /**
     * 启动是否加载JDBC
     */
    private boolean withJdbc;

    /**
     * 启动是否加载Redis
     */
    private boolean withRedis;

    /**
     * 启动是否加载监控
     */
    private boolean monitor;

}