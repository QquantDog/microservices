package com.orlov.restaurantservice.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;


@Entity
@Table(name = "menu_items")
@Cache(region = "menuItemCache", usage = CacheConcurrencyStrategy.READ_WRITE)
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


