//package com.orlov.inventoryservice.model;
//
//import jakarta.persistence.*;
//
//import java.math.BigDecimal;
//
//@Entity
//@Table(name = "basic_items")
//public class Item {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "item_code", unique = true, nullable = false)
//    private String itemCode;
//
//    @Column(name = "basic_price")
//    private BigDecimal basicPrice;
//
//    @Column
//    private String description;
//
//    @Column
//    private String name;
//
//    @Column(name = "proteins")
//    private BigDecimal proteins;
//
//    @Column(name = "fats")
//    private BigDecimal fats;
//
//    @Column(name = "carbs")
//    private BigDecimal carbs;
//
//    @Column(name = "mass")
//    private BigDecimal mass;
//
//}
