package com.orlov.restaurantservice.service;

import com.orlov.restaurantservice.dto.order.ListOrderItemDto;
import com.orlov.restaurantservice.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();
    Item getByItemCode(String itemCode);

    List<Item> getItemsForOrder(ListOrderItemDto listOrderItemDto);

    Item getItemSpecific(String restaurantCode,Long menuId,String itemCode);
}
