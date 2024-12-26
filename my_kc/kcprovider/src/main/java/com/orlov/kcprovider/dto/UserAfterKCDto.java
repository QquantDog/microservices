package com.orlov.kcprovider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAfterKCDto {
    @JsonProperty("customer_uuid")
    @NotNull
    private UUID customerUUID;

    @JsonProperty("email")
    @NotNull
    private String email;

    @JsonProperty("telephone")
    @NotNull
    private String telephone;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("address")
    private String address;
}
