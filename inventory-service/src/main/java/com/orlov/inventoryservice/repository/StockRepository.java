package com.orlov.inventoryservice.repository;

import com.orlov.inventoryservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select s from Stock s join fetch s.inventoryRestaurant")
    List<Stock> getAll();

    @Query("select s from Stock s join fetch s.inventoryRestaurant im where im.restaurantCode = :restaurantCode")
    List<Stock> getAllByRestaurantCode(String restaurantCode);

    @Query("select s from Stock s join s.inventoryRestaurant ir where ir.restaurantCode = :restaurantCode and s.itemCode = :itemCode")
    Stock getStockByItemCodeAndRestaurantCode(String restaurantCode, String itemCode);

    @Query("select s from Stock s join s.inventoryRestaurant ir where ir.restaurantCode = :restaurantCode and s.itemCode in :itemCodes")
    List<Stock> getStockForReserveByRestaurantCode(String restaurantCode, List<String> itemCodes);
}
