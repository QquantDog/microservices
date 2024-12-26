package com.orlov.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartChangeDto {

    @JsonProperty("menu_id")
    private Long menuId;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("amount_change")
    private Integer amountChange;

    @JsonProperty("change_type")
    private String changeType;
}
