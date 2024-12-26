package com.orlov.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orlov.inventoryservice.model.Stock;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {

    private Long id;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("stock_item_total_amount")
    private Integer stockItemTotalAmount;

    @JsonProperty("stock_item_alert_threshold")
    private Integer stockItemAlertThreshold;
}
