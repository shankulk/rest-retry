package com.shankulk.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestApiClient {

    private static final String URL = "https://imdb8.p.rapidapi.com/title/auto-complete?q=deathly%20hallows";

    private final RestTemplate restTemplate;

    public RestApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getImdbTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host", "imdb8.p.rapidapi.com");
        headers.add("x-rapidapi-key", "4405cd3973mshf0172fdb003179dp1194ebjsnfbe28331cced");

        ResponseEntity<String> response = restTemplate
            .exchange(URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        return response.getBody();
    }
}
