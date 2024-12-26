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
public class MenuCreateDto {

    @JsonProperty("name")
    @NotEmpty
    private String name;

    @JsonProperty("description")
    @NotEmpty
    private String description;

//    default activity - true - for simplicity

}
