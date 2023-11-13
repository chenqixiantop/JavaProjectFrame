package com.chenqixian.cloud.common.core.web.config;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
@ConditionalOnClass({ObjectMapper.class})
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class JacksonConfiguration {
    private static final String ASIA_SHANGHAI = "Asia/Shanghai";

    public JacksonConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return (builder) -> {
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            builder.modules(new Module[]{new JavaTimeModule(), new JtsModule()});
        };
    }
}
