package com.orlov.inventoryservice.service;

import com.orlov.inventoryservice.dto.*;
import com.orlov.inventoryservice.dto.order.OrderReserveDto;
import com.orlov.inventoryservice.model.Stock;

import java.util.List;

public interface StockService {
//    List<Stock> getAllStockItemsForAllRestaurants();
    List<Stock> getAllStocksForRestaurantByCode(String restaurantCode);

    List<StockWithItemInfoDto> getAllStockItemsForRestaurantByCode(String restaurantCode);


    void reserveItems(OrderReserveDto orderReserveDto);


    Stock createStock(StockCreateDto stockCreateDto);

    Stock changeItemAmount(StockChangeDto stockChangeDto);

    Stock updateEntry(StockUpdateDto stockUpdateDto);

    void deleteEntry(StockDeleteDto stockDeleteDto);
}
