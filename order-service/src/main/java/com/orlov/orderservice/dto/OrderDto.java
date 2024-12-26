package com.orlov.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer_uuid")
    private UUID customerUUID;

    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

//    @JsonProperty("status")
//    private String status;
}
