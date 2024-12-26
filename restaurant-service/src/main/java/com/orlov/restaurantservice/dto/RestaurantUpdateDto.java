package com.orlov.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import jakarta.annotation.Nonnull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantUpdateDto {

//    @JsonProperty("restaurant_code")
//    private String restaurantCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("telephone")
    private String telephone;
}