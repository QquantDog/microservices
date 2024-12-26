package com.orlov.cart.dto.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    @JsonProperty("cart_id")
    private Long id;

    @JsonProperty("restaurant_code")
    private String restaurantCode;
}
