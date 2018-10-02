package com.lemon.spring.config;

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
public class ConfigProperties {

    public static class Database {
        private String url;
        private String schema;
        private String username;
        private String password;
        private boolean enableStatistics=false;
    }

    public static class Settings {
        private General general;
        private Security security;

        public Security getSecurity() {
            return security;
        }

        public void setSecurity(Security security) {
            this.security = security;
        }

        public General getGeneral() {
            return general;
        }

        public void setGeneral(General general) {
            this.general = general;
        }
        public static class General {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class Security{
            private String secretKey="MY_SECRET_KEY";
            private String enableRememberMe;
            private Double sessionLiveTime;

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
    }
}
