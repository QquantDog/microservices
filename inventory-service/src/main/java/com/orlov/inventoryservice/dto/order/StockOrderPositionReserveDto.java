package com.orlov.inventoryservice.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class StockOrderPositionReserveDto {
    @JsonProperty("item_code")
    private String itemCode;

    @JsonProperty("item_amount")
    @EqualsAndHashCode.Exclude
    private Integer itemAmount;
}
