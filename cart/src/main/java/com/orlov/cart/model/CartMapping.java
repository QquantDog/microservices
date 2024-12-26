package com.orlov.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "cart_mapping")
public class CartMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_uuid")
    private UUID customerUUID;

    @OneToMany(mappedBy = "cartMapping")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Cart> carts = new HashSet<>();

}
