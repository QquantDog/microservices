package com.orlov.cart.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientRestaurant {
    @Bean("RestaurantClient")
    public RestClient customRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8073")
                .build();
    }
}
