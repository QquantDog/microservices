package com.orlov.cart.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientCustomer {
    @Bean("CustomerClient")
    public RestClient customRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:7001")
                .build();
    }
}
