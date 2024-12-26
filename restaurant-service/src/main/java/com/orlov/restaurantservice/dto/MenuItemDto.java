package com.orlov.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDto {
    private Long id;
//
//    @JsonProperty("item_code")
//    private String itemCode;

//    @JsonProperty("final_price")
//    private String finalPrice;

    @JsonProperty("is_low_amount")
    private Boolean isLowAmount;

    @JsonProperty("is_absent")
    private Boolean isAbsent;
}
