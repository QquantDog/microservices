package com.orlov.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantWithMenuDto {

    @JsonProperty("restaurant_id")
    private Long id;

    @JsonProperty("restaurant_code")
    private String restaurantCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("menu_list")
    private List<MenuDto> menuList;
}
