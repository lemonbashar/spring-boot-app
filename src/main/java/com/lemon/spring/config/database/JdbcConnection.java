package com.lemon.spring.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;

/**
 * Created by lemon on 10/14/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate", "SpringAutowiredFieldsWarningInspection"})
@Configuration
public class JdbcConnection {
    @Inject
    private DriverManagerDataSource driverManagerDataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(driverManagerDataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(driverManagerDataSource);
    }

}
