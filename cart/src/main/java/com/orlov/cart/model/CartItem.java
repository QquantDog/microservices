package com.orlov.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "order_stack_amount")
    private Integer stackAmount;

    @Column(name = "basic_price")
    private BigDecimal basicPrice;

    @Column(name = "order_stack_price")
    private BigDecimal stackPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Cart cart;
}
