package com.orlov.inventoryservice.repository;

import com.orlov.inventoryservice.model.InventoryRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRestaurantRepo extends JpaRepository<InventoryRestaurant, Long> {
    Optional<InventoryRestaurant> findByRestaurantCode(String restaurantCode);
}
