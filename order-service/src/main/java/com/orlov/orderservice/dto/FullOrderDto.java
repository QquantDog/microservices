package com.orlov.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullOrderDto {
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

    @JsonProperty("order_items")
    private Set<OrderItemDto> orderItems;
}
