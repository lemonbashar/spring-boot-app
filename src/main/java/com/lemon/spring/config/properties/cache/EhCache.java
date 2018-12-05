package com.lemon.spring.config.properties.cache;

public class EhCache {
    public String maxLocalHeapSize="16M";

    public String getMaxLocalHeapSize() {
        return maxLocalHeapSize;
    }

    public void setMaxLocalHeapSize(String maxLocalHeapSize) {
        this.maxLocalHeapSize = maxLocalHeapSize;
    }
}
