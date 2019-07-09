package com.netflix.tools.koios.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

import static com.netflix.tools.koios.util.KoiosConstants.BEAN_NAME_GITHUB;

@Configuration
public class SpringConfig {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }

    @Bean
    public OkHttpClient getClient() {
        return new OkHttpClient.Builder().build();
    }

    @Bean
    @Qualifier(BEAN_NAME_GITHUB)
    public Request.Builder getRequestBuilder() {
        Request.Builder builder = new Request.Builder();

        if (appProperties.getGithubToken() != null) {
            builder.header("Authorization", appProperties.getGithubToken());
        }

        return builder;
    }

    @Bean
    public ForkJoinPool pool() {
        return new ForkJoinPool(50);
    }
}
