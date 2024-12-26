package com.orlov.orderservice.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPositionDto {

    @JsonProperty("menu_id")
    private Long menuId;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("item_amount")
//    jakarta validation
    private Integer itemAmount;
}
