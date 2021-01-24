package com.shankulk.client;

import org.springframework.http.HttpEntity;

public interface BaseRestApiClient {

    @SuppressWarnings("rawtypes")
    <T> T get(String url, HttpEntity httpEntity, Class<T> responseType,
        Object... urlVariables);
}
