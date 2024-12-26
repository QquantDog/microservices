package com.orlov.restaurantservice.controller;

import com.orlov.restaurantservice.dto.ItemDto;
import com.orlov.restaurantservice.dto.RestaurantDto;
import com.orlov.restaurantservice.dto.RestaurantWithMenuDto;
import com.orlov.restaurantservice.model.Item;
import com.orlov.restaurantservice.model.Restaurant;
import com.orlov.restaurantservice.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/search/all")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(mapToRestaurantDto(restaurants), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantWithMenuDto>> getAllRestaurantsWithMenus() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurantsWithMenus();
        return new ResponseEntity<>(mapToRestaurantWithMenuDto(restaurants), HttpStatus.OK);
    }

    @GetMapping("/exists/{restaurantCode}")
    public ResponseEntity<Void> existsRestaurantByCode(@PathVariable String restaurantCode) {
        restaurantService.existsRestaurantByCode(restaurantCode);
        return ResponseEntity.ok().build();
    }


    private List<RestaurantDto> mapToRestaurantDto(List<Restaurant> restaurants){
        return restaurants.stream().map(r -> modelMapper.map(r, RestaurantDto.class)).toList();
    }

    private List<RestaurantWithMenuDto> mapToRestaurantWithMenuDto(List<Restaurant> restaurants){
        return restaurants.stream().map(r -> modelMapper.map(r, RestaurantWithMenuDto.class)).toList();
    }
}
