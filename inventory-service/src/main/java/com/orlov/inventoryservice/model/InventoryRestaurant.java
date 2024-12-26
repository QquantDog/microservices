package com.orlov.inventoryservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_restaurant")
public class InventoryRestaurant {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "restaurant_code", unique = true, nullable = false)
    private String restaurantCode;
}
