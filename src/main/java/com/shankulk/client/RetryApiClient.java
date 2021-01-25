package com.shankulk.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RetryApiClient implements BaseRestApiClient {

    private final RetryTemplate retryTemplate;

    public RetryApiClient(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }


    @Override
    @SuppressWarnings("rawtypes")
    public <T> T get(RestTemplate restTemplate, String url, HttpEntity httpEntity, Class<T> responseType, Object... urlVariables) {
        ResponseEntity<T> responseEntity = retryTemplate
            .execute(context -> restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                responseType, urlVariables));

        return responseEntity.getBody();
    }

}
