package com.orlov.orderservice.repository;

import com.orlov.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems")
    List<Order> getAllOrdersWithDetails();

    @Query("select o from Order o join fetch o.orderItems where o.customerUUID = :customerUUID")
    List<Order> getOrdersByCustomerUUID(UUID customerUUID);
}
