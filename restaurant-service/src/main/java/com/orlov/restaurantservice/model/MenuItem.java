package com.orlov.restaurantservice.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @Column(name = "final_price", nullable = false)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private BigDecimal finalPrice;

    @Column(name = "is_low_amount", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Boolean isLowAmount;

    @Column(name = "is_absent", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Boolean isAbsent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}


