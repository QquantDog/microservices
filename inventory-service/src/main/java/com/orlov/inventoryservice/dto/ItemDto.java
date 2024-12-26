package com.orlov.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

//    private Long id;

    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("basic_price")
    private BigDecimal basicPrice;

    @JsonProperty
    private String description;

    @JsonProperty
    private String name;

    @JsonProperty("proteins")
    private BigDecimal proteins;

    @JsonProperty("fats")
    private BigDecimal fats;

    @JsonProperty("carbs")
    private BigDecimal carbs;

    @JsonProperty("mass")
    private BigDecimal mass;
}
