package com.orlov.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_code")
    private String restaurantCode;

    @Column(name = "cart_price")
    private BigDecimal cartPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_mapping_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CartMapping cartMapping;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CartItem> cartItems = new HashSet<>();
}
