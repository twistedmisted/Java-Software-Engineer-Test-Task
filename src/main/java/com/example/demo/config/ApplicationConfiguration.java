package com.example.demo.config;

import com.example.demo.inerceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(new RequestInterceptor());
    }
}
