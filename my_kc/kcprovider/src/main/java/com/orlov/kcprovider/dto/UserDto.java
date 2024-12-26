package com.orlov.kcprovider.dto;

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
public class UserDto {

    @JsonProperty("customer_uuid")
    private UUID customerUUID;

    @JsonProperty("preferred_email")
    private String email;

    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;
}
