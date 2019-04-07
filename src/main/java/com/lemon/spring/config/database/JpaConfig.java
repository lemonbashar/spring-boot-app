package com.lemon.spring.config.database;

import com.lemon.framework.properties.spring.ApplicationProperties;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.lemon.spring.repository",entityManagerFactoryRef = "jpaManager")
public class JpaConfig {
    @Inject
    private ApplicationProperties properties;

    @Primary/*Cause There's have more than one entityManagerFactory thus, we have to priority based bean creation*/
    @Bean("jpaManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter vendorAdapter) {
        LocalContainerEntityManagerFactoryBean factoryBean=new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPersistenceUnitName(properties.database.jpa.persistentUnit);
        factoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(HibernateConfig.annotatedPackages);
        factoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
        adapter.setShowSql(properties.database.hibernate.showSql);
        adapter.setDatabasePlatform(properties.database.hibernate.dialect);
        adapter.setGenerateDdl(properties.database.hibernate.formatSQL);
        adapter.setDatabase(Database.POSTGRESQL);

        return adapter;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager hibernateTransactionManager(final LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory.getObject());

        return hibernateTransactionManager;
    }
}
