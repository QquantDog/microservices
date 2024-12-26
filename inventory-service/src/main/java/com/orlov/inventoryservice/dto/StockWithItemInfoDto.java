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
public class StockWithItemInfoDto {

    private Long id;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("stock_item_total_amount")
    private Integer stockItemTotalAmount;

    @JsonProperty("stock_item_alert_threshold")
    private Integer stockItemAlertThreshold;

    @JsonProperty("item")
    private ItemDto item;
}