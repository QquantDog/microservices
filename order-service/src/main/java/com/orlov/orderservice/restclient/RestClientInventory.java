package com.orlov.orderservice.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientInventory {
    @Bean("InventoryClient")
    public RestClient customRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8074")
                .build();
    }
}
