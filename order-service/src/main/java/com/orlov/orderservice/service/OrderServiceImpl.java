package com.orlov.orderservice.service;

import com.orlov.orderservice.dto.create.CreateOrderDto;
import com.orlov.orderservice.dto.ItemDto;
import com.orlov.orderservice.model.Order;
import com.orlov.orderservice.model.OrderItem;
import com.orlov.orderservice.repository.OrderItemRepository;
import com.orlov.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier("RestaurantClient")
    private RestClient restaurantClient;

    @Autowired
    @Qualifier("InventoryClient")
    private RestClient inventoryClient;

    @Autowired
    @Qualifier("CustomerClient")
    private RestClient customerClient;

    private static final ParameterizedTypeReference<List<ItemDto>> itemListTypeReference
            = new ParameterizedTypeReference<List<ItemDto>>() {};

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrdersWithDetails();
    }

    @Override
    public List<Order> getFullOrderByCustomerUUID(UUID customerUUID) {
        return orderRepository.getOrdersByCustomerUUID(customerUUID);
    }

    @Override
    public Order createOrder(CreateOrderDto createOrderDto) {

        UUID uuid = createOrderDto.getCustomerUUID();
        ResponseEntity<Void> customerResponse = customerClient
                .get()
                .uri("/api/v1/user/exists/" + uuid.toString())
                .header("uuid", uuid.toString())
                .header("roles", "ROLE_SERVICE")
//                .accept(APPLICATION_JSON)
                .retrieve().toBodilessEntity();

        if(customerResponse.getStatusCode().isError()) throw new RuntimeException("Customer not found by UUID");
//        только endpoint будет не все получить - а получить инфу о
//        продуктах из списка заказываемых
        List<ItemDto> itemsDto = restaurantClient
                .post()
                .uri("/api/v1/item/order-positions")
                .accept(APPLICATION_JSON)
                .body(createOrderDto)
                .retrieve().toEntity(itemListTypeReference).getBody();
//                .body(itemListTypeReference);

        if(itemsDto == null) throw new RuntimeException("Error get response from restaurant/item endpoint - result IS NULL");

        int positionOrderDTOLength = createOrderDto.getOrderPositions().size();
        if(positionOrderDTOLength < itemsDto.size()) {
            throw new RuntimeException("Some Order positions are not available - pls recheck cart");
        }
        ResponseEntity<Void> resp = inventoryClient.post()
                .uri("/api/v1/stock/order-reserve")
                .accept(APPLICATION_JSON)
                .body(createOrderDto)
                .retrieve().toBodilessEntity();
        if(resp.getStatusCode().isError()) throw new RuntimeException("Error reserving items in stock");

//        првоерить бы что пользователь существует но так как он будет взят их хедера
//        то покс НО проверить надо бы конечно
        Order order = Order.builder()
                .customerUUID(createOrderDto.getCustomerUUID())
                .restaurantCode(createOrderDto.getRestaurantCode())
                .build();
        HashMap<String, ItemDto> mapItems = new HashMap<>();
        itemsDto.forEach(i -> {mapItems.put(i.getItemCode(), i);});

        Set<OrderItem> orderItems = createOrderDto.getOrderPositions()
                .stream().map(op -> {
                    OrderItem orderItem = modelMapper.map(op, OrderItem.class);
                    orderItem.setOrder(order);
                    orderItem.setMenuId(op.getMenuId());
                    orderItem.setStackAmount(op.getItemAmount());

                    BigDecimal tempPrice = mapItems.get(op.getItemCode()).getBasicPrice();
                    BigDecimal finalPrice = tempPrice.multiply(BigDecimal.valueOf(op.getItemAmount()));

                    orderItem.setStackPrice(finalPrice);
                    return orderItem;
                }).collect(Collectors.toSet());
        order.setOrderItems(orderItems);
//        BigDecimal totalPrice = order.setTotalPrice(orderItems.stream().map(OrderItem::getStackPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(var oi: orderItems){
            totalPrice = totalPrice.add(oi.getStackPrice());
        }
        order.setTotalPrice(totalPrice);

//        orderItemRepository.saveAllAndFlush(orderItems);
        return orderRepository.save(order);
    }
}
