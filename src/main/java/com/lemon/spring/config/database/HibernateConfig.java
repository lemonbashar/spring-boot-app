package com.lemon.spring.config.database;

import com.lemon.framework.orm.capture.hbm.HbmCapture;
import com.lemon.framework.orm.capture.hbm.impl.HibernateCapture;
import com.lemon.framework.properties.spring.ApplicationProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by lemon on 10/21/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    static final String[] annotatedPackages = {"com.lemon.spring.domain", "com.lemon.spring.domain.internal"};
    static final String[] mappingFiles = {"config/hibernate/mapping/token.hbm.xml"};
    @Inject
    private ApplicationProperties properties;
    private Logger log = LogManager.getLogger(HibernateConfig.class);

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        Properties hbmProperties = new Properties();
        hbmProperties.setProperty(Environment.DIALECT, properties.database.hibernate.dialect);
        hbmProperties.setProperty(Environment.HBM2DDL_AUTO, properties.database.hibernate.hbm2DDLAuto);
        hbmProperties.setProperty(Environment.USE_SQL_COMMENTS, "" + properties.database.hibernate.comments);
        hbmProperties.setProperty(Environment.SHOW_SQL, "" + properties.database.hibernate.showSql);
        hbmProperties.setProperty(Environment.FORMAT_SQL, "" + properties.database.hibernate.formatSQL);
        hbmProperties.setProperty(Environment.USE_SECOND_LEVEL_CACHE, "" + properties.database.hibernate.enableSecondLevelCache);
        hbmProperties.setProperty(Environment.USE_QUERY_CACHE, "" + properties.database.hibernate.enableQueryCache);
        hbmProperties.setProperty(Environment.AUTO_EVICT_COLLECTION_CACHE, "" + properties.database.hibernate.enableAutoEvictCollCache);
        hbmProperties.setProperty(Environment.USE_STRUCTURED_CACHE, "" + properties.database.hibernate.enableStruturedCache);
        hbmProperties.setProperty(Environment.CACHE_REGION_FACTORY, "" + properties.database.hibernate.secondLevelCacheRegionFactoryClass);
        //hbmProperties.setProperty(Environment.CACHE_PROVIDER_CONFIG,"config/cache/ehcache.xml");
//        hbmProperties.setProperty("net.sf.ehcache.configurationResourceName","config/cache/ehcache.xml");
        localSessionFactoryBean.setPhysicalNamingStrategy(new AllCapitalPhysicalNaming());
        //hbmProperties.setProperty(Environment.DEFAULT_SCHEMA,properties.database.schema);
        localSessionFactoryBean.setPackagesToScan(annotatedPackages);
        //localSessionFactoryBean.setMappingResources(mappingFiles);
        localSessionFactoryBean.setHibernateProperties(hbmProperties);
        //localSessionFactoryBean.setEntityInterceptor(interceptor);
        return localSessionFactoryBean;

    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

    @Bean
    public HbmCapture hbmCapture(SessionFactory sessionFactory) {
        return new HibernateCapture(sessionFactory, properties.database.hibernate.enableSecondLevelCache);
    }
}
