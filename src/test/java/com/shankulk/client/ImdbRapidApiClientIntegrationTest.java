package com.shankulk.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ImdbRapidApiClientIntegrationTest {

  @MockBean private RestTemplate restTemplate;

  @Autowired private ImdbRapidApiClient imdbRapidApiClient;

  private static Set<HttpStatus> allowedHttpStatusCodes() {
    return Set.of(BAD_GATEWAY, SERVICE_UNAVAILABLE, INTERNAL_SERVER_ERROR, GATEWAY_TIMEOUT);
  }

  @ParameterizedTest
  @MethodSource("allowedHttpStatusCodes")
  void retriableExceptions_testRetryWorks(HttpStatus httpStatus) {
//    given(
//            restTemplate.exchange(
//                anyString(),
//                eq(HttpMethod.GET),
//                any(HttpEntity.class),
//                eq(String.class),
//                any(Object[].class)))
//        .willThrow(new HttpServerErrorException(httpStatus));

    Assertions.assertThrows(HttpServerErrorException.class, () -> imdbRapidApiClient.getImdbTitle());

//    verify(restTemplate, times(2))
//        .exchange(
//            anyString(),
//            eq(HttpMethod.GET),
//            any(HttpEntity.class),
//            eq(String.class),
//            any(Object[].class));
  }

  @ParameterizedTest
  @EnumSource(
      value = HttpStatus.class,
      names = {"BAD_GATEWAY", "SERVICE_UNAVAILABLE", "INTERNAL_SERVER_ERROR", "GATEWAY_TIMEOUT"},
      mode = Mode.EXCLUDE)
  void clientErrorException_testNoRetries(HttpStatus httpStatus) {
//    given(
//            restTemplate.exchange(
//                anyString(),
//                eq(HttpMethod.GET),
//                any(HttpEntity.class),
//                eq(String.class),
//                any(Object[].class)))
//        .willThrow(new HttpClientErrorException(httpStatus));

    Assertions.assertThrows(HttpClientErrorException.class, () -> imdbRapidApiClient.getImdbTitle());

//    verify(restTemplate, times(1))
//        .exchange(
//            anyString(),
//            eq(HttpMethod.GET),
//            any(HttpEntity.class),
//            eq(String.class),
//            any(Object[].class));
  }
}
