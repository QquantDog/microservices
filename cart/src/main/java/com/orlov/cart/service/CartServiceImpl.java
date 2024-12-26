package com.orlov.cart.service;

import com.orlov.cart.dto.AddItemToCartDto;
import com.orlov.cart.dto.CartChangeDto;
import com.orlov.cart.dto.ItemDto;
import com.orlov.cart.dto.RemoveItemFromCart;
import com.orlov.cart.model.Cart;
import com.orlov.cart.model.CartItem;
import com.orlov.cart.model.CartMapping;
import com.orlov.cart.repository.CartItemRepository;
import com.orlov.cart.repository.CartMappingRepository;
import com.orlov.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    @Lazy
    private CartService cartSelfInjectionService;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestClient restClient;

    @Autowired
    private CartMappingRepository cartMappingRepository;

//    private static final ParameterizedTypeReference<List<ItemDto>> itemListTypeReference
//            = new ParameterizedTypeReference<List<ItemDto>>() {};

    @Override
    public Cart getCartWithInfo(UUID customerUUID, String restaurantCode) {
        return cartRepository.getCartWithItemsInfo(customerUUID, restaurantCode)
                .orElseThrow(()->new RuntimeException("Cart not found"));
    }

    @Override
    @Transactional
    public Cart changeCart(UUID customerUUID, String restaurantCode, CartChangeDto cartChangeDto) {
        Cart cart = cartRepository.getCartWithItemsInfo(customerUUID, restaurantCode)
                .orElseThrow(()->new RuntimeException("Cart not found"));
//        считаем что раз итем уже в карте то просто меняем его количество НЕ ВЗИРАЯ на инвентарное кол-во
//        проверять КОЛ-ВО будет order-service - только на заказа а не на каждый чих
        Set<CartItem> cartItems = cart.getCartItems();
        boolean isItemFound = false;
        for(CartItem cartItem : cartItems) {
            if(cartItem.getItemCode().equals(cartChangeDto.getItemCode())) {
                isItemFound = true;
                switch (cartChangeDto.getChangeType()){
                    case "add": {
//                        ВОзомжно хибер не подтянет изменения
                        BigDecimal deltaPrice = BigDecimal.valueOf(cartChangeDto.getAmountChange()).multiply(cartItem.getBasicPrice());
                        cartItem.setStackPrice(cartItem.getStackPrice().add(deltaPrice));
                        cartItem.setStackAmount(cartItem.getStackAmount() + cartChangeDto.getAmountChange());
//                        cartItemRepository.save(cartItem);
                        cart.setCartPrice(cart.getCartPrice().add(deltaPrice));
                        break;
                    }
                    case "remove": {
                        if(cartItem.getStackAmount() <= cartChangeDto.getAmountChange()) throw new RuntimeException("cart items couldn't be negative or zero");
                        BigDecimal deltaPrice = BigDecimal.valueOf(cartChangeDto.getAmountChange()).multiply(cartItem.getBasicPrice());
                        cartItem.setStackPrice(cartItem.getStackPrice().subtract(deltaPrice));
                        cartItem.setStackAmount(cartItem.getStackAmount() - cartChangeDto.getAmountChange());
                        cart.setCartPrice(cart.getCartPrice().subtract(deltaPrice));
                        break;
                    }
                    default: {
                        throw new RuntimeException("Invalid change type");
                    }
                }
                break;
            }
        }
        if(isItemFound) return cartRepository.save(cart);
        else throw new RuntimeException("Please add item to cart before change amount");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Cart createCartMapping(UUID customerUUID, String restaurantCode) {
        ResponseEntity<Void> existsRest;
        try{
            existsRest = restClient.get()
                    .uri("api/v1/restaurant/exists/" + restaurantCode)
                    .retrieve().toBodilessEntity();
        } catch (Exception e){
            throw new RuntimeException("Couldn't parse answer from restaurant-ms (exist request)");
        }

        if(existsRest.getStatusCode().isError()) throw new RuntimeException("Restaurant not found while checking by RestCode");


        CartMapping cm = cartMappingRepository.findCartMappingByCustomerUUID(customerUUID);
        if(cm == null) {
            cm = new CartMapping();
            cm.setCustomerUUID(customerUUID);
        }
        Cart cartToSave = new Cart();
        cartToSave.setRestaurantCode(restaurantCode);
        cartToSave.setCartPrice(BigDecimal.ZERO);
        cartToSave.setCartMapping(cm);
        cm.getCarts().add(cartToSave);
        cartMappingRepository.saveAndFlush(cm);
        return cartRepository.saveAndFlush(cartToSave);
    }

    @Override
    @Transactional
    public Cart addItemToCart(UUID customerUUID, String restaurantCode, AddItemToCartDto addItemToCartDto) {
        Optional<Cart> cartOptional = cartRepository.getCartWithItemsInfo(customerUUID, restaurantCode);
        Cart cart = cartOptional.orElse(null);
        if(cartOptional.isEmpty()) {
//            проверить что пользователь существует(можно не проверять так как оно идет с SecureHeader'a)
//            провоерить что ресторан существует

            cart = cartSelfInjectionService.createCartMapping(customerUUID, restaurantCode);
        }
        if(cart == null) throw new RuntimeException("Cart not found");

//        Cart cart = cartRepository.getCartWithItemsInfo(customerUUID, restaurantCode)
//                .orElseThrow(()->new RuntimeException("Cart not found"));


//        Сходить в ресторан проверить что такой итем существует и добавить его, если его еще нет
//        в ну
        ResponseEntity<ItemDto> response = null;
        try{
            response = restClient.get()
                    .uri("/api/v1/item/" + addItemToCartDto.getItemCode() + "/" + restaurantCode + "/" + addItemToCartDto.getMenuId())
                    .accept(APPLICATION_JSON)
                    .retrieve().toEntity(ItemDto.class);
        } catch (Exception e){
            throw new RuntimeException("Couldn't parse answer from restaurant-ms (add request)");
        }
        if(response.getStatusCode().isError()) throw new RuntimeException("Check item in Restaurant/Items ms failed");

        ItemDto itemDto = response.getBody();
        if(itemDto == null) throw new RuntimeException("ItemDto is null");
        if(!itemDto.getItemCode().equals(addItemToCartDto.getItemCode())) throw new RuntimeException("Mismatch item code");

        Set<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems) {
            if(cartItem.getItemCode().equals(addItemToCartDto.getItemCode())) {
                throw new RuntimeException("Item already exists");
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setItemCode(addItemToCartDto.getItemCode());
        cartItem.setStackAmount(1);
        cartItem.setStackPrice(itemDto.getBasicPrice());
        cartItem.setMenuId(addItemToCartDto.getMenuId());
        cartItem.setBasicPrice(itemDto.getBasicPrice());
        cartItem.setCart(cart);
        //        пересчет цены корзины
        cart.setCartPrice(cart.getCartPrice().add(itemDto.getBasicPrice()));

        cart.getCartItems().add(cartItem);

        cartItemRepository.save(cartItem);
        return cartRepository.saveAndFlush(cart);
    }

    @Override
    @Transactional
    public void delItemFromCart(UUID customerUUID, String restaurantCode, RemoveItemFromCart removeItemFromCart) {
        Cart cart = cartRepository.getCartWithItemsInfo(customerUUID, restaurantCode)
                .orElseThrow(()->new RuntimeException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream().filter((ci)->ci.getItemCode().equals(removeItemFromCart.getItemCode()))
                .findFirst().orElseThrow(()-> new RuntimeException("Cart item not found"));
        cart.setCartPrice(cart.getCartPrice().subtract(cartItem.getStackPrice()));
        cartItemRepository.delete(cartItem);
    }

}
