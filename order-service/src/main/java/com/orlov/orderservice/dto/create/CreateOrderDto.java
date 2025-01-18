package com.orlov.orderservice.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDto {

    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("order_positions")
    private List<OrderPositionDto> orderPositions;
}
