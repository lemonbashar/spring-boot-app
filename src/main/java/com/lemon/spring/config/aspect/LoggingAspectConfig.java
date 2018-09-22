package com.lemon.spring.config.aspect;

import com.lemon.spring.aop.logging.LoggingAspect;
import com.lemon.spring.config.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = Constants.PROFILE_DEVELOPMENT)
@EnableAspectJAutoProxy
public class LoggingAspectConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
