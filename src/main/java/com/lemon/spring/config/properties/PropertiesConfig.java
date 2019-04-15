package com.lemon.spring.config.properties;

import com.lemon.framework.properties.spring.ApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class PropertiesConfig {

    @Bean("applicationProperties")
    @ConfigurationProperties(prefix = "spring-app")
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }
}
