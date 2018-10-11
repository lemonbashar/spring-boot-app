package com.lemon.spring.config.properties.settings;

public class Security{
    public String secretKey="MY_SECRET_KEY";
    public String enableRememberMe;
    public Double sessionLiveTime;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEnableRememberMe() {
        return enableRememberMe;
    }

    public void setEnableRememberMe(String enableRememberMe) {
        this.enableRememberMe = enableRememberMe;
    }

    public Double getSessionLiveTime() {
        return sessionLiveTime;
    }

    public void setSessionLiveTime(Double sessionLiveTime) {
        this.sessionLiveTime = sessionLiveTime;
    }
}
