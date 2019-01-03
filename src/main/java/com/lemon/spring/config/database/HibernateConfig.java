package com.lemon.spring.config.database;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.orm.capture.hbm.impl.HibernateCapture;
import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.spring.controller.rest.AccountControllerRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.Environment;
import org.hibernate.type.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by lemon on 10/21/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Inject
    private ApplicationProperties properties;

    private Logger log= LogManager.getLogger(AccountControllerRest.class);

    static final String[] annotatedPackages={"com.lemon.spring.domain"};
    static final String[] mappingFiles={"config/hibernate/mapping/token.hbm.xml"};

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
        localSessionFactoryBean.setPhysicalNamingStrategy(new AllCapitalPhysicalNaming());
        //hbmProperties.setProperty(Environment.DEFAULT_SCHEMA,properties.database.schema);
        localSessionFactoryBean.setPackagesToScan(annotatedPackages);
        //localSessionFactoryBean.setMappingResources(mappingFiles);
        localSessionFactoryBean.setHibernateProperties(hbmProperties);
        //localSessionFactoryBean.setEntityInterceptor(interceptor);
        return localSessionFactoryBean;

    }

    /*@Bean
    public LocalEntityManagerFactoryBean localEntityManagerFactoryBean(DataSource dataSource,JpaVendorAdapter vendorAdapter) {
        LocalEntityManagerFactoryBean factoryBean=new LocalEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabasePlatform(properties.database.hibernate.dialect);
        adapter.setGenerateDdl(true);
        return adapter;
    }*/

    /*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean=new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPackagesToScan(annotatedPackages);
        return bean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabasePlatform(properties.database.hibernate.dialect);
        adapter.setGenerateDdl(true);
        return adapter;
    }*/

    /*@Bean("transactionManager")
    public PlatformTransactionManager hibernateTransactionManager(final LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory.getObject());

        return hibernateTransactionManager;
    }*/

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

    @Bean
    public HbmCapture hbmCapture(SessionFactory sessionFactory) {
        return new HibernateCapture(sessionFactory);
    }
}
