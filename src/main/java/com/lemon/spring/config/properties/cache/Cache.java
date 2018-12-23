package com.lemon.spring.config.properties.cache;

public class Cache {
    public Long timeToLiveSeconds=3600L;

    public EhCache ehCache=new EhCache();

    public Long getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(Long timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    public EhCache getEhCache() {
        return ehCache;
    }

    public void setEhCache(EhCache ehCache) {
        this.ehCache = ehCache;
    }
}
