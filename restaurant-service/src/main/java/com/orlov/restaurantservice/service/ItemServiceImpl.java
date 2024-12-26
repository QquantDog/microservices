package com.orlov.restaurantservice.service;

import com.orlov.restaurantservice.dto.order.ListOrderItemDto;
import com.orlov.restaurantservice.dto.order.OrderPositionDto;
import com.orlov.restaurantservice.model.Item;
import com.orlov.restaurantservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ItemServiceImpl implements ItemService {

    private static Logger logger = Logger.getLogger(ItemServiceImpl.class.getName());

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item getByItemCode(String itemCode) {
//        or else thorw бла бла бла
        return itemRepository.getItemByItemCode(itemCode)
                .orElseThrow(()->new RuntimeException("Item not found"));
    }

    @Override
    public List<Item> getItemsForOrder(ListOrderItemDto listOrderItemDto) {
        String restaurantCode = listOrderItemDto.getRestaurantCode();

        List<Item> items = new ArrayList<>();

        List<OrderPositionDto> orderPositions = listOrderItemDto.getOrderPositions();

        for (OrderPositionDto position : orderPositions) {
            Optional<Item> itemOptional = itemRepository
                    .findItemByItemCodeAndMenuIdForRestaurant(restaurantCode, position.getMenuId(), position.getItemCode());
            itemOptional.ifPresentOrElse(items::add, ()->{
//                logger.info("Item not found in Restaurant/Item database by product_code");
                System.out.println("Item not found in Restaurant/Item database by product_code");
            });
        }
        return items;
    }

    @Override
    public Item getItemSpecific(String restaurantCode, Long menuId, String itemCode) {
        return itemRepository.findItemByItemCodeAndMenuIdForRestaurant(restaurantCode, menuId, itemCode)
                .orElseThrow(() ->new RuntimeException("No item fooun for specific position requirements"));
    }
}



//        listOrderItemDto.getOrderPositions().forEach(orderPosition -> {
//            items.add(itemRepository
//                    .findItemByItemCodeAndMenuIdForRestaurant(restaurantCode, orderPosition.getMenuId(), orderPosition.getItemCode())
//                        .orElseThrow(()->new RuntimeException("Item not found")));
//        });
