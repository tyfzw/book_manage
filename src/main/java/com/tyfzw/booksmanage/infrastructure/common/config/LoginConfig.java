package com.tyfzw.booksmanage.infrastructure.common.config;


import com.tyfzw.booksmanage.application.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-03-21
 * Time: 18:38
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;


   @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(adminInterceptor)
               .excludePathPatterns("/user/login")
               .excludePathPatterns("/user/doLogin");
    }
}
