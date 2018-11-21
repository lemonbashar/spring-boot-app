package com.lemon.spring.config.database;

import com.lemon.spring.config.properties.ApplicationProperties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by lemon on 10/21/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})

@Configuration
@EnableJpaRepositories(basePackages = "com.lemon.spring.repository")
@EnableTransactionManagement
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
        localSessionFactoryBean.setPhysicalNamingStrategy(new AllCapitalPhysicalNaming());
        //hbmProperties.setProperty(Environment.DEFAULT_SCHEMA,properties.database.schema);
        localSessionFactoryBean.setPackagesToScan(annotatedPackages);
        localSessionFactoryBean.setHibernateProperties(hbmProperties);
        return localSessionFactoryBean;

    }

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


}
