package com.orlov.restaurantservice.service;

import com.orlov.restaurantservice.dto.RestaurantCreateDto;
import com.orlov.restaurantservice.dto.RestaurantUpdateDto;
import com.orlov.restaurantservice.model.Menu;
import com.orlov.restaurantservice.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getAllRestaurantsWithMenus();

    Restaurant createRestaurant(RestaurantCreateDto restaurantCreateDto);
    Restaurant updateRestaurant(Long restaurantId, RestaurantUpdateDto restaurantUpdateDto);

    void existsRestaurantByCode(String restaurantCode);

    void deleteRestaurant(Long restaurantId);
}
