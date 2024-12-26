package com.orlov.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockChangeDto {

    private Long id;

    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("type")
    private String changeType;

    @JsonProperty("stock_change")
    private Integer stockChange;
}
