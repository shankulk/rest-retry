package com.shankulk.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApiClientIntegrationTest {

  @Value("${retry.max-attempts}")
  private int maxRetryAttempts;

  @MockBean private RestTemplate restTemplate;

  @Autowired private RestApiClient restApiClient;

  @Test
  void nonClientErrorException_testRetryWorks() {
    given(
            restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class),
                any(Object[].class)))
        .willThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

    Assertions.assertThrows(HttpServerErrorException.class, () -> restApiClient.getImdbTitle());

    verify(restTemplate, times(maxRetryAttempts))
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class),
            any(Object[].class));
  }

  @Test
  void clientErrorException_testNoRetries() {
    given(
            restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class),
                any(Object[].class)))
        .willThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

    Assertions.assertThrows(HttpClientErrorException.class, () -> restApiClient.getImdbTitle());

    verify(restTemplate, times(1))
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class),
            any(Object[].class));
  }
}
