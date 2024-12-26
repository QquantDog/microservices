package com.orlov.restaurantservice.controller;

import com.orlov.restaurantservice.dto.MenuDto;
import com.orlov.restaurantservice.model.Menu;
import com.orlov.restaurantservice.service.MenuService;
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
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("{restaurantId}/menu/search/all")
    public ResponseEntity<List<MenuDto>> getAllMenus(@PathVariable("restaurantId") Long restaurantId) {
        List<Menu> menus = menuService.getAllMenusForRestaurant(restaurantId);
        return new ResponseEntity<>(mapToMenuDto(menus), HttpStatus.OK);
    }

    private List<MenuDto> mapToMenuDto(List<Menu> menus){
        return menus.stream().map(m -> modelMapper.map(m, MenuDto.class)).toList();
    }
}
