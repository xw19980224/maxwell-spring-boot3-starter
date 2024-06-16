package com.xw.framework.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "maxwell.boot.with-redis", havingValue = "false", matchIfMissing = true)
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class})
public class ExcludeRedisConfiguration {
}
