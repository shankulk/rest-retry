package com.shankulk.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class RestApiClient {

    private static final String URL = "https://imdb8.p.rapidapi.com/title/auto-complete?q=deathly%20hallows";

    private final RetryApiClient retryApiClient;

    @Value("${rapid-api-key}")
    private String rapidApiKey;

    public RestApiClient(RetryApiClient retryApiClient) {
        this.retryApiClient = retryApiClient;
    }

    public String getImdbTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host", "imdb8.p.rapidapi.com");
        headers.add("x-rapidapi-key", rapidApiKey);

        String response = retryApiClient
            .get(URL, new HttpEntity<>(headers), String.class);

        return response;
    }
}
