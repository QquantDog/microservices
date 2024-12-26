package com.orlov.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCreateDto {

    @JsonProperty("restaurant_code")
    @NotEmpty
    private String restaurantCode;

    @JsonProperty("name")
    @NotEmpty
    private String name;

    @JsonProperty("address")
    @NotEmpty
    private String address;

    @JsonProperty("telephone")
    @NotEmpty
    private String telephone;
}