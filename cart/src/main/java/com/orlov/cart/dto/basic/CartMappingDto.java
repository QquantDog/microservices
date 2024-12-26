package com.orlov.cart.dto.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartMappingDto {

    @JsonProperty("mapping_id")
    private Long id;

    @JsonProperty("customer_uuid")
    private UUID customerUUID;
}