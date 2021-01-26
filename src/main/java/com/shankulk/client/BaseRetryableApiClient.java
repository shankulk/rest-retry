package com.shankulk.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class BaseRetryableApiClient {

    @Autowired
    private RetryTemplate retryTemplate;

    @SuppressWarnings("rawtypes")
    protected final <T> T get(RestTemplate restTemplate, String url, HttpEntity httpEntity,
        Class<T> responseType, Object... urlVariables) {
        ResponseEntity<T> responseEntity = retryTemplate
            .execute(context -> restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                responseType, urlVariables));

        return responseEntity.getBody();
    }

}
