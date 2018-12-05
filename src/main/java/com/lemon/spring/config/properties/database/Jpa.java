package com.lemon.spring.config.properties.database;

public class Jpa {
    public String persistentUnit="SPRING";

    public String getPersistentUnit() {
        return persistentUnit;
    }

    public void setPersistentUnit(String persistentUnit) {
        this.persistentUnit = persistentUnit;
    }
}
