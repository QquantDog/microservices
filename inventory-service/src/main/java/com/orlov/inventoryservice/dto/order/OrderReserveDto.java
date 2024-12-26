package com.orlov.inventoryservice.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReserveDto {
    @JsonProperty("restaurant_code")
    @NotNull
    private String restaurantCode;

    @JsonProperty("order_positions")
    @NotNull
    private List<StockOrderPositionReserveDto> orderPositions;
}
