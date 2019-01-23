package com.lemon.spring.config.supportmodule;

import com.lemon.framework.statistics.generator.random.impl.RandomString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.inject.Singleton;


@Configuration
public class Statistics {

    @Bean
    @Scope("singleton")
    @Singleton
    public RandomString randomString() {
        return new RandomString(5).includeAlphabet().includeNumbers();
    }
}
