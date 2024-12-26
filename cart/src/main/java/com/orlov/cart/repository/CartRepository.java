package com.orlov.cart.repository;

import com.orlov.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join c.cartMapping cm join fetch c.cartItems ci where cm.customerUUID = :customerUUID and c.restaurantCode = :restaurantCode")
    Optional<Cart> getCartWithItemsInfo(UUID customerUUID, String restaurantCode);
}
