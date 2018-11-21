package com.lemon.spring.config.database;

import com.lemon.spring.config.properties.ApplicationProperties;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by lemon on 10/21/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
public class HibernateConfig {
    @Inject
    private ApplicationProperties properties;
    private final String[] annotatedPackages={"com.lemon.spring.domain"};

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean=new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        Properties hbmProperties=new Properties();
        hbmProperties.setProperty(Environment.DIALECT,properties.database.hibernate.dialect);
        hbmProperties.setProperty(Environment.HBM2DDL_AUTO,properties.database.hibernate.hbm2DDLAuto);
        hbmProperties.setProperty(Environment.USE_SQL_COMMENTS,""+properties.database.hibernate.comments);
        hbmProperties.setProperty(Environment.SHOW_SQL,""+properties.database.hibernate.showSql);
        hbmProperties.setProperty(Environment.FORMAT_SQL,""+properties.database.hibernate.formatSQL);
        localSessionFactoryBean.setAnnotatedPackages(annotatedPackages);
        localSessionFactoryBean.setHibernateProperties(hbmProperties);
        return localSessionFactoryBean;

    }
}
