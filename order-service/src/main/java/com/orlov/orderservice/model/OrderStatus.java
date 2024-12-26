package com.orlov.orderservice.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED,
    DELIVERED,
    PREPARING,
    IN_WAY,
    CANCELLED
}
