package com.lemon.spring.config.database;

import com.lemon.framework.properties.spring.ApplicationProperties;
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
        dataSource.setUrl(applicationProperties.database.url);
        dataSource.setDriverClassName(applicationProperties.database.driver);
        dataSource.setUsername(applicationProperties.database.username);
        dataSource.setPassword(applicationProperties.database.password);
        Properties properties=new Properties();
        properties.setProperty("initialSize",applicationProperties.database.initialConnectionSize);
        properties.setProperty("maxActive",applicationProperties.database.maximumActiveConnectionSize);
        properties.setProperty("maxIdle",applicationProperties.database.maxIdleConnectionSize);
        dataSource.setConnectionProperties(properties);
        //if(!applicationProperties.getDatabase().getSchema().isEmpty())dataSource.setSchema(applicationProperties.getDatabase().getSchema());
    }

}
