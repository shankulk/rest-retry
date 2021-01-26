package com.shankulk.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class ImdbRapidApiClient extends BaseRetryableApiClient {

    private static final String URL = "https://imdb8.p.rapidapi.com/title/auto-complete?q=deathly%20hallows";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rapid-api-key}")
    private String rapidApiKey;

    public String getImdbTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host", "imdb8.p.rapidapi.com");
        headers.add("x-rapidapi-key", rapidApiKey);

        return get(restTemplate, URL, new HttpEntity<>(headers), String.class);
    }
}
