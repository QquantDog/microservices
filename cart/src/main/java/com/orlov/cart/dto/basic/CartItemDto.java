package com.orlov.cart.dto.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("menu_id")
    private Long menuId;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("basic_price")
    private BigDecimal basicPrice;

    @JsonProperty("order_stack_amount")
    private Integer stackAmount;

    @JsonProperty("order_stack_price")
    private BigDecimal stackPrice;
}
