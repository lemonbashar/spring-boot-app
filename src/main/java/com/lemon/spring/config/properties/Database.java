package com.lemon.spring.config.properties;

public class Database {
    public String url;
    public String schema;
    public String username;
    public String password;
    public String driver;
    public boolean enableStatistics=false;
    public String initialConnectionSize="0";
    public String maximumActiveConnectionSize="8";
    public String maxIdleConnectionSize="8";

    public Hibernate hibernate;
    public Jpa jpa;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Hibernate getHibernate() {
        return hibernate;
    }

    public void setHibernate(Hibernate hibernate) {
        this.hibernate = hibernate;
    }

    public boolean isEnableStatistics() {
        return enableStatistics;
    }

    public void setEnableStatistics(boolean enableStatistics) {
        this.enableStatistics = enableStatistics;
    }

    public String getInitialConnectionSize() {
        return initialConnectionSize;
    }

    public void setInitialConnectionSize(String initialConnectionSize) {
        this.initialConnectionSize = initialConnectionSize;
    }

    public String getMaximumActiveConnectionSize() {
        return maximumActiveConnectionSize;
    }

    public void setMaximumActiveConnectionSize(String maximumActiveConnectionSize) {
        this.maximumActiveConnectionSize = maximumActiveConnectionSize;
    }

    public String getMaxIdleConnectionSize() {
        return maxIdleConnectionSize;
    }

    public void setMaxIdleConnectionSize(String maxIdleConnectionSize) {
        this.maxIdleConnectionSize = maxIdleConnectionSize;
    }

    public Jpa getJpa() {
        return jpa;
    }

    public void setJpa(Jpa jpa) {
        this.jpa = jpa;
    }
}
