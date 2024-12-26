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
public class StockUpdateDto {

    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("stock_item_total_amount")
    private Integer stockItemTotalAmount;

    @JsonProperty("stock_item_alert_threshold")
    private Integer stockItemAlertThreshold;
}
