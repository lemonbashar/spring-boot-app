package com.lemon.spring.config.database;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

/**
 * Created by lemon on 10/21/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
public class HibernateConfig {
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean=new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        return localSessionFactoryBean;

    }
}
