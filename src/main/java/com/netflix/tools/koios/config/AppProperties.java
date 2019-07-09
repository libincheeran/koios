package com.netflix.tools.koios.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class AppProperties {
    private String githubBaseUrl;
    private String githubToken;

    public String getGithubBaseUrl() {
        return githubBaseUrl;
    }

    public void setGithubBaseUrl(String githubBaseUrl) {
        this.githubBaseUrl = githubBaseUrl;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }
}
