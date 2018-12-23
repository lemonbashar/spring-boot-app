package com.lemon.spring.config.properties;

import com.lemon.spring.config.properties.cache.Cache;
import com.lemon.spring.config.properties.database.Database;
import com.lemon.spring.config.properties.settings.Settings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lemon on 10/2/18.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationProperties {
    public Settings settings;
    public Database database;
    public Cache cache=new Cache();

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}

