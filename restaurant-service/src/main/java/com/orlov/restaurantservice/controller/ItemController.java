package com.orlov.restaurantservice.controller;

import com.orlov.restaurantservice.dto.ItemDto;
import com.orlov.restaurantservice.dto.order.ListOrderItemDto;
import com.orlov.restaurantservice.model.Item;
import com.orlov.restaurantservice.service.ItemService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("all")
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<Item> items = itemService.getAll();
        return new ResponseEntity<>(mapToItemDto(items), HttpStatus.OK);
    }

    @GetMapping("/{itemCode}")
    public ResponseEntity<ItemDto> getItem(@PathVariable String itemCode) {
        Item item = itemService.getByItemCode(itemCode);
        return new ResponseEntity<>(modelMapper.map(item, ItemDto.class), HttpStatus.OK);
//        return new ResponseEntity<>(mapToItemDto(items), HttpStatus.OK);
    }

    @GetMapping("/{itemCode}/{restaurantCode}/{menuId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable("itemCode") String itemCode,
                                           @PathVariable("menuId") Long menuId,
                                           @PathVariable("restaurantCode") String restaurantCode) {
        Item item = itemService.getItemSpecific(restaurantCode, menuId, itemCode);
        return new ResponseEntity<>(modelMapper.map(item, ItemDto.class), HttpStatus.OK);
    }

//    да получим резалт по post'y
    @PostMapping("/order-positions")
    public ResponseEntity<List<ItemDto>> getOrderPositionsInfo(@RequestBody @Valid ListOrderItemDto listOrderItemDto) {
        List<Item> itemsForOrder = itemService.getItemsForOrder(listOrderItemDto);
        return new ResponseEntity<>(mapToItemDto(itemsForOrder), HttpStatus.OK);
    }

    private List<ItemDto> mapToItemDto(List<Item> items){
        return items.stream().map(i -> modelMapper.map(i, ItemDto.class)).toList();
    }
}
