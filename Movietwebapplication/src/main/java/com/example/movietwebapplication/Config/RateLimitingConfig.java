package com.example.movietwebapplication.Config;


import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitingConfig {

    @Bean
    public FilterRegistrationBean<Filter> rateLimitingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitFilter());
        registrationBean.addUrlPatterns("/api/*"); // all api path
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

