package com.orlov.cart.service;

import com.orlov.cart.dto.AddItemToCartDto;
import com.orlov.cart.dto.CartChangeDto;
import com.orlov.cart.dto.RemoveItemFromCart;
import com.orlov.cart.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartService {
    Cart getCartWithInfo(UUID customerUUID, String restaurantCode);

    Cart changeCart(UUID customerUUID, String restaurantCode, CartChangeDto cartChangeDto);

    Cart addItemToCart(UUID customerUUID, String restaurantCode, AddItemToCartDto addItemToCartDto);

    void delItemFromCart(UUID customerUUID, String restaurantCode, RemoveItemFromCart removeItemFromCart);

    Cart createCartMapping(UUID customerUUID, String restaurantCode);
}
