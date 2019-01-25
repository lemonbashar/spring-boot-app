package com.lemon.spring.config.supportmodule;

import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.framework.statistics.generator.random.impl.RandomString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;


@Configuration
public class Statistics {
    @Inject
    private ApplicationProperties properties;

    @Bean
    public RandomString randomString() {
        return new RandomString(properties.settings.security.account.password.recoveryCodeLength).includeAlphabet().includeNumbers();
    }
}
