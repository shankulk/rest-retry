package com.shankulk.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

public interface BaseRestApiClient {

    @SuppressWarnings("rawtypes")
    <T> T get(String url, HttpMethod method, HttpEntity httpEntity, Class<T> responseType,
        Object... urlVariables);
}
