package com.learn.config;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("elasticsearch")
public class ElasticSearchConfig {
    private String host = "";
    private String username = "task-";
    private String password = "task-";
    private String basePackage = "task-";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}

