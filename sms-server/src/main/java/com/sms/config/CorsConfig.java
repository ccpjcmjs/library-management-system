package com.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 前端报错 Access-Control-Allow-Origin 的前端问题解决 解决跨域问题
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST","DELETE","PUT")
                .allowedHeaders("token")
                .maxAge(3600);
    }
}
