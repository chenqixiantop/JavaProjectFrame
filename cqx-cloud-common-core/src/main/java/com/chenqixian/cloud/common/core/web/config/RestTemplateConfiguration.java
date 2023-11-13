package com.chenqixian.cloud.common.core.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    public RestTemplateConfiguration() {
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
