package com.orlov.inventoryservice.service;

import com.orlov.inventoryservice.dto.*;
import com.orlov.inventoryservice.dto.order.OrderReserveDto;
import com.orlov.inventoryservice.dto.order.StockOrderPositionReserveDto;
import com.orlov.inventoryservice.model.InventoryRestaurant;
import com.orlov.inventoryservice.model.Stock;
import com.orlov.inventoryservice.repository.InventoryRestaurantRepo;
import com.orlov.inventoryservice.repository.StockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class StockServiceImpl implements StockService {

    private static final ParameterizedTypeReference<List<ItemDto>> itemListTypeReference
            = new ParameterizedTypeReference<List<ItemDto>>() {};

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private InventoryRestaurantRepo inventoryRestaurantRepository;

    @Autowired
    private RestClient restClient;
    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public List<Stock> getAllStockItemsForAllRestaurants() {
//        return stockRepository.getAll();
//    }

    @Override
    public List<Stock> getAllStocksForRestaurantByCode(String restaurantCode) {
        return stockRepository.getAllByRestaurantCode(restaurantCode);
    }

    @Override
    public List<StockWithItemInfoDto> getAllStockItemsForRestaurantByCode(String restaurantCode) {
        List<Stock> stocks = stockRepository.getAllByRestaurantCode(restaurantCode);

        List<StockDto> stocksDto = stocks.stream().map(s -> modelMapper.map(s, StockDto.class)).toList();
        List<ItemDto> itemsDto = restClient.get().uri("/api/v1/item/all").accept(APPLICATION_JSON)
                .retrieve()
                .body(itemListTypeReference);

        if (itemsDto == null) { throw new RuntimeException("itemsDto is null"); }
        if(stocksDto.isEmpty() || itemsDto.isEmpty()) return new ArrayList<>();

        HashMap<String, StockWithItemInfoDto> mapStockWithItemByKey = new HashMap<>();


        stocksDto.forEach(s -> {
            String codeId = s.getItemCode();
            if(mapStockWithItemByKey.get(codeId) == null) {
                StockWithItemInfoDto stockWithItemInfoDto = new StockWithItemInfoDto();
                modelMapper.map(s, stockWithItemInfoDto);
                mapStockWithItemByKey.put(codeId, stockWithItemInfoDto);
            } else{
                modelMapper.map(s, mapStockWithItemByKey.get(codeId));
            }
        });
        itemsDto.forEach(i -> {
            String codeId = i.getItemCode();
            if(mapStockWithItemByKey.get(codeId) == null) {
                StockWithItemInfoDto stockWithItemInfoDto = new StockWithItemInfoDto();
                stockWithItemInfoDto.setItem(i);
                mapStockWithItemByKey.put(codeId, stockWithItemInfoDto);
            } else{
                mapStockWithItemByKey.get(codeId).setItem(i);
//                modelMapper.map(i, mapStockWithItemByKey.get(codeId));
            }
        });

        return mapStockWithItemByKey.values().stream().toList();
    }

    @Override
    @Transactional
    public void reserveItems(OrderReserveDto orderReserveDto) {
//
        List<Stock> stocksToReserve = stockRepository.getStockForReserveByRestaurantCode(orderReserveDto.getRestaurantCode(),
                orderReserveDto.getOrderPositions().stream().map(StockOrderPositionReserveDto::getItemCode).toList());
        if (stocksToReserve.size() < orderReserveDto.getOrderPositions().size()) throw new RuntimeException("Stock reservation don't have some types");

        HashMap<String, Stock> mapStock = new HashMap<>();
        stocksToReserve.forEach(s -> {mapStock.put(s.getItemCode(), s);});

        orderReserveDto.getOrderPositions().forEach(op -> {
            Stock stockToReserve = mapStock.get(op.getItemCode());
            if(stockToReserve.getStockItemTotalAmount() < op.getItemAmount())
                throw new RuntimeException("Stock - " + op.getItemCode()
                        + " reservation impossible: stock amount - "
                        + stockToReserve.getStockItemTotalAmount()
                        + " order reserve amount: " + op.getItemAmount());
            Integer resultItemAmount = stockToReserve.getStockItemTotalAmount() - op.getItemAmount();
            stockToReserve.setStockItemTotalAmount(resultItemAmount);
            if(resultItemAmount < stockToReserve.getStockItemTotalAmount()){
//                здесь послать в kafkу сообщение что сток ниже порога
                System.out.println("Stock - " + op.getItemCode() + " is below alert threshold: " + stockToReserve.getStockItemTotalAmount());
            }
        });
        System.out.println("Stock reservation done for restaurant: " + orderReserveDto.getRestaurantCode());

//        stocksToReserve.forEach(stock -> {})

//        var q = 5;
    }

    @Override
    @Transactional
    public Stock createStock(StockCreateDto stockCreateDto) {
        Stock stock = new Stock();
        InventoryRestaurant r = inventoryRestaurantRepository.findByRestaurantCode(stockCreateDto.getRestaurantCode())
                .orElseThrow(()-> new RuntimeException("Restaurant/Inventory not found by code"));
        Stock stockCheckIfPresent = stockRepository.getStockByItemCodeAndRestaurantCode(stockCreateDto.getRestaurantCode(), stockCreateDto.getItemCode());
        if (stockCheckIfPresent != null) throw new RuntimeException("Stock already exists");


//        СХОДИТЬ В ITEMS проверить сущестует ли такой сток вообще
        ItemDto itemDto;
        try{
            itemDto = restClient.get().uri("/api/v1/item/"+stockCreateDto.getItemCode())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .body(ItemDto.class);
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Item not found by code");
        }
        if(itemDto == null) throw new RuntimeException("item does not exist");

        modelMapper.map(stockCreateDto, stock);
        stock.setInventoryRestaurant(r);
        return stockRepository.save(stock);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    с констраинтом можно и read_commited
    public Stock changeItemAmount(StockChangeDto stockChangeDto) {
        Stock stock = stockRepository.getStockByItemCodeAndRestaurantCode(stockChangeDto.getRestaurantCode(), stockChangeDto.getItemCode());
        if(stock == null) throw new RuntimeException("stock not found");

        String changeType = stockChangeDto.getChangeType();
        switch (changeType) {
            case "add":{
                stock.setStockItemTotalAmount(stock.getStockItemTotalAmount() + stockChangeDto.getStockChange());
                break;
            }
            case "subtract":{
                if(stock.getStockItemTotalAmount() < stockChangeDto.getStockChange()) {
                    throw new RuntimeException("stock_subtract is less than the stock");
                } else{
                    stock.setStockItemTotalAmount(stock.getStockItemTotalAmount() - stockChangeDto.getStockChange());
                }
                break;
            }
            default:
                throw new RuntimeException("changeType is not supported");
        }
//        здесь может быть код который если кол-во минимально то сток посылает месседж кафке что стоки пустеют
        return stockRepository.saveAndFlush(stock);
    }

    @Override
    @Transactional
    public Stock updateEntry(StockUpdateDto stockUpdateDto) {
        Stock stock = stockRepository.getStockByItemCodeAndRestaurantCode(stockUpdateDto.getRestaurantCode(), stockUpdateDto.getItemCode());
        if(stock == null) throw new RuntimeException("stock not found");

        modelMapper.map(stockUpdateDto, stock);
        return stockRepository.saveAndFlush(stock);
    }

    @Override
    @Transactional
    public void deleteEntry(StockDeleteDto stockDeleteDto) {
        Stock stock = stockRepository.getStockByItemCodeAndRestaurantCode(stockDeleteDto.getRestaurantCode(), stockDeleteDto.getItemCode());
        if(stock == null) throw new RuntimeException("stock not found");
//        if(stock.getStockItemTotalAmount() > 0) throw new RuntimeException("stock items are not 0, pls either deplete or force change to 0");
        stockRepository.delete(stock);
    }
}
