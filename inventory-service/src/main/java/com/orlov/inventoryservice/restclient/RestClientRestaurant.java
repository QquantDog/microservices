package com.orlov.inventoryservice.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientRestaurant {
    @Bean
    public RestClient customRestClient() {
        return RestClient.builder()
//                поменять на property в yml
                .baseUrl("http://localhost:8073")
                .build();
    }
}
