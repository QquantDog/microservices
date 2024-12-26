package com.orlov.restaurantservice.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOrderItemDto {
    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("order_positions")
    private List<OrderPositionDto> orderPositions;
}
