package com.rogerstore.ecomorderservice.config;

import com.rogerstore.ecomorderservice.exception.CustomErrorDecoder;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class InventoryFeignClientConfig {

    @Bean
    public Logger.Level feignLoggerInvLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options  options()
    {
        return  new Request.Options(3000,5000);
    }


    @Bean
    public Retryer retryer(){
        return new Retryer.Default(1L,2L,3);
    }

    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return requestTemplate ->   {
            requestTemplate.header("x-Correlation-Id", UUID.randomUUID().toString());
        };
    }


    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new CustomErrorDecoder();
    }
}
