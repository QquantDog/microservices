package com.orlov.orderservice.service;

import com.orlov.orderservice.dto.create.CreateOrderDto;
import com.orlov.orderservice.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getFullOrderByCustomerUUID(UUID customerUUID);

    Order createOrder(CreateOrderDto createOrderDto);
}
