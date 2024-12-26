package com.orlov.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("menu_id")
    private Long menuId;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("order_stack_amount")
    private Integer stackAmount;

    @JsonProperty("order_stack_price")
    private BigDecimal stackPrice;
}
