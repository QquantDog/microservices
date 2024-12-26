package com.orlov.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orlov.cart.dto.basic.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCartDto {
    @JsonProperty("cart_id")
    private Long id;

    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("cart_price")
    private BigDecimal cartPrice;

    @JsonProperty("cart_items")
    private Set<CartItemDto> cartItems = new HashSet<>();
}
