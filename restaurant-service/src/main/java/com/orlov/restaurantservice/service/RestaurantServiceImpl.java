package com.orlov.restaurantservice.service;

import com.orlov.restaurantservice.dto.RestaurantCreateDto;
import com.orlov.restaurantservice.dto.RestaurantUpdateDto;
import com.orlov.restaurantservice.model.Restaurant;
import com.orlov.restaurantservice.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getAllRestaurantsWithMenus(){
        return restaurantRepository.getAllRestaurantsWithMenus();
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(RestaurantCreateDto restaurantCreateDto) {
        return restaurantRepository
                .saveAndFlush(modelMapper.map(restaurantCreateDto, Restaurant.class));
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(Long restaurantId, RestaurantUpdateDto restaurantUpdateDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()->new RuntimeException("Restaurant not found"));
        modelMapper.map(restaurantUpdateDto, restaurant);
        return restaurantRepository.saveAndFlush(restaurant);
    }

    @Override
    @Transactional
    public void existsRestaurantByCode(String restaurantCode) {
        restaurantRepository.findRestaurantByRestaurantCode(restaurantCode)
                .orElseThrow(()-> new RuntimeException("Restaurant not found"));
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
//        or find + throw if not found + delete
        restaurantRepository.deleteById(restaurantId);
    }
}
