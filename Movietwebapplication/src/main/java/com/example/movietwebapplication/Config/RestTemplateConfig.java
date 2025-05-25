package com.example.movietwebapplication.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean //to fetch json HTTP request and handling the response
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean // to convert between java  to json object
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

