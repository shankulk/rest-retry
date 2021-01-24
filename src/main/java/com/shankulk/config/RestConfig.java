package com.shankulk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Value("${retry.max-attempts:2}")
    private int maxRetryAttempts;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        policy.setExceptionClassifier(this::getClassifier);
        retryTemplate.setRetryPolicy(policy);

        return retryTemplate;
    }

    private RetryPolicy getClassifier(Throwable throwable) {
        if (irrecoverableError(throwable)) {
            return new NeverRetryPolicy();
        }
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(maxRetryAttempts);
        return simpleRetryPolicy;
    }

    private boolean irrecoverableError(Throwable throwable) {
        return throwable instanceof HttpClientErrorException;
    }


}
