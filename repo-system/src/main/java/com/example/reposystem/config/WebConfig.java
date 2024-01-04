package com.example.reposystem.config;

import com.example.reposystem.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//注入拦截器，使其对网页生效
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //对login和register两个接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/test");
    }
}
