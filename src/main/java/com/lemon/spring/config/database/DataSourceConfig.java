package com.lemon.spring.config.database;

import com.lemon.spring.config.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import java.util.Properties;

/**
 * Created by lemon on 10/11/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate", "SpringAutowiredFieldsWarningInspection"})
@Configuration
public class DataSourceConfig {
    @Inject
    private ApplicationProperties applicationProperties;

    @Bean
    /*Use If Every Request You Need to access the Database Rapidly, Cause it gives the new connection for every request*/
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        injectProperties(dataSource);
        return dataSource;
    }

    private void injectProperties(DriverManagerDataSource dataSource) {
        dataSource.setUrl(applicationProperties.getDatabase().getUrl());
        dataSource.setDriverClassName(applicationProperties.getDatabase().getDriver());
        dataSource.setUsername(applicationProperties.database.getUsername());
        dataSource.setPassword(applicationProperties.getDatabase().getPassword());
        Properties properties=new Properties();
        properties.setProperty("initialSize",applicationProperties.getDatabase().getInitialConnectionSize());
        properties.setProperty("maxActive",applicationProperties.getDatabase().getMaximumActiveConnectionSize());
        properties.setProperty("maxIdle",applicationProperties.getDatabase().getMaxIdleConnectionSize());
        dataSource.setConnectionProperties(properties);
        //if(!applicationProperties.getDatabase().getSchema().isEmpty())dataSource.setSchema(applicationProperties.getDatabase().getSchema());
    }

}
