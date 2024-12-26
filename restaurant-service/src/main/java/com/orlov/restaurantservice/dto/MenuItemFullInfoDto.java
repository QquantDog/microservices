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
public class MenuItemFullInfoDto {
    private Long id;
//
//    @JsonProperty("final_price")
//    private String finalPrice;

    @JsonProperty("is_low_amount")
    private Boolean isLowAmount;

    @JsonProperty("is_absent")
    private Boolean isAbsent;


}
