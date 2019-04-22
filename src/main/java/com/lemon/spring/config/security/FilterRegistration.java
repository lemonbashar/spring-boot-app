package com.lemon.spring.config.security;

import com.lemon.framework.security.auth.AuthorizationBridge;
import com.lemon.framework.springsecurity.filter.sha.SHA1Filter;
import com.lemon.spring.config.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;

@Configuration
public class FilterRegistration {

    @Profile({Constants.PROFILE_X_SECURE, Constants.PROFILE_PRODUCTION})
    @Bean
    @Order(1)
    public Filter sha1Filter(AuthorizationBridge authorizationBridge) {
        return new SHA1Filter(authorizationBridge);
    }
}
