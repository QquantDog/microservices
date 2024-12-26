package com.orlov.restaurantservice.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String name;

    @Column(name = "description")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String description;

    @Column(name = "is_active", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    private Set<MenuItem> menuItems = new HashSet<>();;

//    1 - many - menu_items
}
