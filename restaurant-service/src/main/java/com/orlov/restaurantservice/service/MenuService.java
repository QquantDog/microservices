package com.orlov.restaurantservice.service;

import com.orlov.restaurantservice.model.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuService {

//    @Query("select m from Menu m join Restaurant r where r.id = :restaurantId")
//    @Query("select m from Menu m where m.restaurant.id = :restaurantId")
    List<Menu> getAllMenusForRestaurant(Long restaurantId);
}
