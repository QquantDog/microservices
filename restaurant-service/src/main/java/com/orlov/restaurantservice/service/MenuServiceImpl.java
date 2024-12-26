package com.orlov.restaurantservice.service;

import com.orlov.restaurantservice.model.Menu;
import com.orlov.restaurantservice.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> getAllMenusForRestaurant(Long restaurantId){
        return menuRepository.getAllMenusForRestaurant(restaurantId);
    }
}
