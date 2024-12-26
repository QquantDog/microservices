package com.orlov.restaurantservice.controller;

import com.orlov.restaurantservice.dto.RestaurantCreateDto;
import com.orlov.restaurantservice.dto.RestaurantDto;
import com.orlov.restaurantservice.dto.RestaurantUpdateDto;
import com.orlov.restaurantservice.model.Restaurant;
import com.orlov.restaurantservice.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/restaurant")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    private ResponseEntity<RestaurantDto> createRestaurant(@RequestBody @Validated RestaurantCreateDto restaurantCreateDto) {
        Restaurant result = restaurantService.createRestaurant(restaurantCreateDto);
        return new ResponseEntity<>(modelMapper.map(result, RestaurantDto.class), HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("restaurantId") Long restaurantId, @RequestBody @Validated RestaurantUpdateDto restaurantUpdateDto) {
        Restaurant result = restaurantService.updateRestaurant(restaurantId, restaurantUpdateDto);
        return new ResponseEntity<>(modelMapper.map(result, RestaurantDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //
    private List<RestaurantDto> mapToRestaurantDto(List<Restaurant> restaurants){
        return restaurants.stream().map(r -> modelMapper.map(r, RestaurantDto.class)).toList();
    }


}
