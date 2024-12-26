package com.orlov.inventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_stock_details")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    для связи с микросервисом ресторанов
    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "stock_item_total_amount")
    private Integer stockItemTotalAmount;

    @Column(name = "stock_item_alert_threshold")
    private Integer stockItemAlertThreshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryRestaurant inventoryRestaurant;


//    @ManyToOne
//    @JoinColumn(name = "item_id")
//    private Item item;
}
