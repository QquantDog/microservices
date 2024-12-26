package com.orlov.inventoryservice.controller;

import com.orlov.inventoryservice.dto.*;
import com.orlov.inventoryservice.dto.order.OrderReserveDto;
import com.orlov.inventoryservice.model.Stock;
import com.orlov.inventoryservice.service.StockService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{restaurantCode}")
    public ResponseEntity<List<StockDto>> getAllStocks(@PathVariable String restaurantCode) {
        List<Stock> stocksForRestaurant = stockService.getAllStocksForRestaurantByCode(restaurantCode);
        return new ResponseEntity<>(mapToStockDto(stocksForRestaurant), HttpStatus.OK);
    }

    @GetMapping("/full/{restaurantCode}")
    public ResponseEntity<List<StockWithItemInfoDto>> getAllStocksWithItemInfo(@PathVariable String restaurantCode) {
        List<StockWithItemInfoDto> stocks = stockService.getAllStockItemsForRestaurantByCode(restaurantCode);
        StockDto q = new StockDto();
//        return new ResponseEntity<>(mapToStockDto(stocks), HttpStatus.OK);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @PostMapping("/order-reserve")
    public ResponseEntity<?> reserveStock(@RequestBody @Valid OrderReserveDto orderReserveDto) {
        stockService.reserveItems(orderReserveDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/create")
    public ResponseEntity<StockDto> createStock(@RequestBody StockCreateDto stockCreateDto) {
        Stock stock = stockService.createStock(stockCreateDto);
        return new ResponseEntity<>(modelMapper.map(stock, StockDto.class), HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<StockDto> changeStock(@RequestBody StockChangeDto stockChangeDto) {
        Stock stock = stockService.changeItemAmount(stockChangeDto);
        return new ResponseEntity<>(modelMapper.map(stock, StockDto.class), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<StockDto> updateStock(@RequestBody StockUpdateDto stockUpdateDto) {
        Stock stock = stockService.updateEntry(stockUpdateDto);
        return new ResponseEntity<>(modelMapper.map(stock, StockDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteStock(@RequestBody StockDeleteDto stockDeleteDto) {
        stockService.deleteEntry(stockDeleteDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<StockDto> mapToStockDto(List<Stock> stocks) {
        return stocks.stream().map(s -> modelMapper.map(s, StockDto.class)).toList();
    }

}
