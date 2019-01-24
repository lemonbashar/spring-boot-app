package com.lemon.spring.config.supportmodule;

import com.lemon.framework.statistics.generator.random.impl.RandomString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Statistics {

    @Bean
    public RandomString randomString() {
        return new RandomString(5).includeAlphabet().includeNumbers();
    }
}
