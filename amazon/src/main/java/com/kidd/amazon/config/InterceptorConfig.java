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
                .addPathPatterns("/**")
//                .addPathPatterns(
//                        "/version2/mobile/signin",
//                        "/version2/mobile/homepage/billing",
//                        "/version2/mobile/homepage/card",
//                        "/version2/mobile/homepage/secure",
//                        "/version2/mobile/homepage/success",
//                        "/thanks",
//                        "/verified",
//                        "/user-verified",
//                        "/kiddbilling",
//                        "/user-bill",
//                        "/warn",
//                        "/kiddSigin",
//                        "/signin",
//                        "/"
//                        )
                .excludePathPatterns("/aa/**","/assets/**","/library/**",
                        "/css/**","/sheets/**","/style/**","/robots.txt","/test","/favicon.ico"
                        ,"/gp/**","/error/**","/thanks","/count","/crawlAmazonTester","/sendSms"
                        ,"/version2/mobile/js/**",
                        "/version2/mobile/style/**",
                        "/version2/mobile/homepage/card/**");

    }

}
