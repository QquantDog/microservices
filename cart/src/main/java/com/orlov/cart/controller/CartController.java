package com.orlov.cart.controller;

import com.orlov.cart.dto.AddItemToCartDto;
import com.orlov.cart.dto.CartChangeDto;
import com.orlov.cart.dto.RemoveItemFromCart;
import com.orlov.cart.dto.RestaurantCartDto;
import com.orlov.cart.model.Cart;
import com.orlov.cart.service.CartService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("customer/{customerUUID}/restaurant/{restaurantCode}")
    public ResponseEntity<RestaurantCartDto> getCartItems(@PathVariable("customerUUID") UUID customerUUID,
                                                     @PathVariable("restaurantCode") String restaurantCode){
        Cart cart = cartService.getCartWithInfo(customerUUID, restaurantCode);
        return ResponseEntity.ok(convertCartToRestaurantCartDto(cart));
    }

    @PostMapping("customer/{customerUUID}/restaurant/{restaurantCode}")
    public ResponseEntity<RestaurantCartDto> changeCartItem(@PathVariable("customerUUID") UUID customerUUID,
                                                            @PathVariable("restaurantCode") String restaurantCode,
                                                            @RequestBody @Valid CartChangeDto cartChangeDto){
        Cart cart = cartService.changeCart(customerUUID, restaurantCode, cartChangeDto);
        return ResponseEntity.ok(convertCartToRestaurantCartDto(cart));
    }

    @PostMapping("customer/{customerUUID}/restaurant/{restaurantCode}/add-to-cart")
    public ResponseEntity<RestaurantCartDto> addItemToCart(@PathVariable("customerUUID") UUID customerUUID,
                                                            @PathVariable("restaurantCode") String restaurantCode,
                                                            @RequestBody @Valid AddItemToCartDto addItemToCartDto){
        Cart cart = cartService.addItemToCart(customerUUID, restaurantCode, addItemToCartDto);
        return ResponseEntity.ok(convertCartToRestaurantCartDto(cart));
    }
    @PostMapping("customer/{customerUUID}/restaurant/{restaurantCode}/del-from-cart")
    public ResponseEntity<?> addItemToCart(@PathVariable("customerUUID") UUID customerUUID,
                                                           @PathVariable("restaurantCode") String restaurantCode,
                                                           @RequestBody @Valid RemoveItemFromCart removeItemFromCart){
        cartService.delItemFromCart(customerUUID, restaurantCode, removeItemFromCart);
        return ResponseEntity.noContent().build();
    }

    private RestaurantCartDto convertCartToRestaurantCartDto(Cart cart){
        return modelMapper.map(cart, RestaurantCartDto.class);
    }

}
