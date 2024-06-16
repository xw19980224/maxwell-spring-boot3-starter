package com.xw.framework.config;

import com.xw.framework.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by MaxWell on 2024/6/6 09:57
 */
@Configuration
public class MaxwellWebConfig implements WebMvcConfigurer {

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/doc.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
//    可以具体制定哪些需要拦截，哪些不拦截，其实也可以使用自定义注解更灵活完成
//                .addPathPatterns("/**")
//                .excludePathPatterns("/testxx.html");
    }
}
