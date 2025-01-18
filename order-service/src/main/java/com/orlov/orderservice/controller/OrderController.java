package com.orlov.orderservice.controller;

import com.orlov.orderservice.dto.create.CreateOrderDto;
import com.orlov.orderservice.dto.FullOrderDto;
import com.orlov.orderservice.model.Order;
import com.orlov.orderservice.service.OrderService;
import com.orlov.orderservice.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<FullOrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<FullOrderDto> ordersDto = orders.stream().map(this::mapToFullOrderDto).toList();
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }

    @GetMapping("/{customerUUID}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<FullOrderDto>> getAllCustomerOrders(@PathVariable("customerUUID") UUID customerUUID) {
        List<Order> orders = orderService.getFullOrderByCustomerUUID(customerUUID);
        List<FullOrderDto> ordersDto = orders.stream().map(this::mapToFullOrderDto).toList();
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<List<FullOrderDto>> getMyOrders() {
        List<Order> orders = orderService.getFullOrderByCustomerUUID(SecurityUtils.getContextUserUUID());
        List<FullOrderDto> ordersDto = orders.stream().map(this::mapToFullOrderDto).toList();
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        Order order = orderService.createOrder(createOrderDto);
        return new ResponseEntity<>(modelMapper.map(order, FullOrderDto.class), HttpStatus.CREATED);
    }

    private FullOrderDto mapToFullOrderDto(Order order) {
        return modelMapper.map(order, FullOrderDto.class);
    }

}
