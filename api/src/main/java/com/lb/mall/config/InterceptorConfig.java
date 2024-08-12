package com.lb.mall.config;

import com.lb.mall.interceptor.CheckTokenInterceptor;
import com.lb.mall.interceptor.SetTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;
    @Autowired
    private SetTimeInterceptor setTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(checkTokenInterceptor)
                 .addPathPatterns("/shopcart/**")
                 .addPathPatterns("/orders/**")
                 .addPathPatterns("/useraddr/**")
                 .addPathPatterns("/user/check");
         registry.addInterceptor(setTimeInterceptor).addPathPatterns("/**");
    }
}
