package com.shankulk.config;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {


  @Value("${retry.max-attempts:3}")
  private int retryAttempts;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();
    ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
    policy.setPolicyMap(configureRetryPoliciesPerException());
    retryTemplate.setRetryPolicy(policy);

    return retryTemplate;
  }

  private Map<Class<? extends Throwable>, RetryPolicy> configureRetryPoliciesPerException() {
    return Map.of(HttpServerErrorException.class, new SimpleRetryPolicy(retryAttempts),
        HttpClientErrorException.class, new NeverRetryPolicy());
  }
}
