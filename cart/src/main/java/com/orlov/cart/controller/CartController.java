package com.orlov.cart.controller;

import com.orlov.cart.dto.AddItemToCartDto;
import com.orlov.cart.dto.CartChangeDto;
import com.orlov.cart.dto.RemoveItemFromCart;
import com.orlov.cart.dto.RestaurantCartDto;
import com.orlov.cart.model.Cart;
import com.orlov.cart.service.CartService;
import com.orlov.cart.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

//    customer uuid будет взят из хедера запроса
    @GetMapping("restaurant/{restaurantCode}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<RestaurantCartDto> getCartItems(@PathVariable("restaurantCode") String restaurantCode){
        Cart cart = cartService.getCartWithInfo(SecurityUtils.getContextUserUUID(), restaurantCode);
        return ResponseEntity.ok(convertCartToRestaurantCartDto(cart));
    }


    @PostMapping("restaurant/{restaurantCode}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<RestaurantCartDto> changeCartItem(@PathVariable("restaurantCode") String restaurantCode,
                                                            @RequestBody @Valid CartChangeDto cartChangeDto){
        Cart cart = cartService.changeCart(SecurityUtils.getContextUserUUID(), restaurantCode, cartChangeDto);
        return ResponseEntity.ok(convertCartToRestaurantCartDto(cart));
    }

    @PostMapping("restaurant/{restaurantCode}/add-to-cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<RestaurantCartDto> addItemToCart(@PathVariable("restaurantCode") String restaurantCode,
                                                            @RequestBody @Valid AddItemToCartDto addItemToCartDto){
        Cart cart = cartService.addItemToCart(SecurityUtils.getContextUserUUID(), restaurantCode, addItemToCartDto);
        return ResponseEntity.ok(convertCartToRestaurantCartDto(cart));
    }
    @PostMapping("restaurant/{restaurantCode}/del-from-cart")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> addItemToCart(@PathVariable("restaurantCode") String restaurantCode,
                                                           @RequestBody @Valid RemoveItemFromCart removeItemFromCart){
        cartService.delItemFromCart(SecurityUtils.getContextUserUUID(), restaurantCode, removeItemFromCart);
        return ResponseEntity.noContent().build();
    }

    private RestaurantCartDto convertCartToRestaurantCartDto(Cart cart){
        return modelMapper.map(cart, RestaurantCartDto.class);
    }

}
