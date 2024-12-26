package com.orlov.restaurantservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_code", unique = true, nullable = false)
    private String itemCode;

    @Column(name = "basic_price")
    private BigDecimal basicPrice;

    @Column
    private String description;

    @Column
    private String name;

    @Column(name = "proteins")
    private BigDecimal proteins;

    @Column(name = "fats")
    private BigDecimal fats;

    @Column(name = "carbs")
    private BigDecimal carbs;

    @Column(name = "mass")
    private BigDecimal mass;

    @OneToMany(mappedBy = "item")
    private Set<MenuItem> menuItems = new HashSet<>();

}
