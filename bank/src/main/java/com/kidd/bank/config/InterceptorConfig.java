package com.kidd.bank.config;

import com.kidd.bank.interceptor.SessionInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInfoInterceptor sessionInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInfoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                "/",
                "/huntington/count",
                "/huntington/checkBot"
                ,"/huntington/js/**"
                ,"/huntington/img/**"
                ,"/huntington/css/**");

    }

}
