package com.orlov.restaurantservice.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_code", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String restaurantCode;

    @Column(name = "name", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String name;

    @Column(name = "address", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String address;

    @Column(name = "telephone", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String telephone;

    @OneToMany(mappedBy = "restaurant")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Menu> menuList;
}
