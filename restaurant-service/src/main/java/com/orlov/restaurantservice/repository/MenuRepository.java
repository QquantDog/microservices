package com.orlov.restaurantservice.repository;

import com.orlov.restaurantservice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.restaurant.id = :restaurantId")
    List<Menu> getAllMenusForRestaurant(Long restaurantId);
}
