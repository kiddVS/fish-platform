package com.kidd.smbc.config;

import com.kidd.smbc.interceptor.SessonInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private SessonInfoInterceptor sessonInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessonInfoInterceptor)
                .addPathPatterns("/pc/index",
                        "/pc/card",
                        "/pc/debit",
                        "/pc/finish",
                        "/mobile/index",
                        "/mobile/card",
                        "/mobile/debit",
                        "/mobile/finish");
//                .excludePathPatterns("/aa/**","/assets/**","/library/**",
//                        "/css/**","/sheets/**","/style/**","/robots.txt","/test");
    }

}
