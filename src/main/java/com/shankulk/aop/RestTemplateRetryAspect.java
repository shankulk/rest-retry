package com.shankulk.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RestTemplateRetryAspect {

    private RetryTemplate retryTemplate;

    public RestTemplateRetryAspect(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Around("execution(public * org.springframework.web.client.RestTemplate.*(..))")
    public Object executionTime(final ProceedingJoinPoint point)
        throws Throwable {
        return retryTemplate.execute(context -> point.proceed());
    }
}
