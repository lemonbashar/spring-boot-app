package com.lemon.spring.config.cache;

import com.lemon.spring.config.properties.ApplicationProperties;
import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;

@Configuration
@EnableCaching
public class CacheConfig {
    @Inject
    private ApplicationProperties properties;

    @Bean
    public EhCacheCacheManager ehCacheManager(CacheManager cacheManager) {
        cacheManager.getConfiguration().setMaxBytesLocalHeap(properties.cache.ehCache.maxLocalHeapSize);
        return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean ehCache() {
        EhCacheManagerFactoryBean factoryBean=new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("config/cache/ehcache.xml"));
        return factoryBean;
    }

    /*@Bean
    public CacheManager cacheManager() {
        CacheManager cacheManager = net.sf.ehcache.CacheManager.create();
        cacheManager.getConfiguration().setMaxBytesLocalHeap("16M");
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }*/
}