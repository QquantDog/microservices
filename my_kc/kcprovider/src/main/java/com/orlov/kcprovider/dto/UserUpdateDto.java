package com.orlov.kcprovider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    @JsonProperty("preferred_email")
    @NotNull
    private String email;

    @JsonProperty("telephone")
    @NotNull
    private String telephone;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("address")
    @NotNull
    private String address;
}
