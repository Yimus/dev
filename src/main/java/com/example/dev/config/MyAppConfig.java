package com.example.dev.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "myapp")
public class MyAppConfig {
    private Auth auth;
    private List<String> featureFlags;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public List<String> getFeatureFlags() {
        return featureFlags;
    }

    public void setFeatureFlags(List<String> featureFlags) {
        this.featureFlags = featureFlags;
    }

    public static class Auth {
        private String token;
        private Duration expireTimeout;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Duration getExpireTimeout() {
            return expireTimeout;
        }

        public void setExpireTimeout(Duration expireTimeout) {
            this.expireTimeout = expireTimeout;
        }
    }
}
