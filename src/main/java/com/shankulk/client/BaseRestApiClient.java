package com.shankulk.client;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public interface BaseRestApiClient {

    @SuppressWarnings("rawtypes")
    <T> T get(RestTemplate restTemplate, String url, HttpEntity httpEntity, Class<T> responseType,
        Object... urlVariables);
}
