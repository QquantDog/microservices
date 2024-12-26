package com.orlov.cart.repository;

import com.orlov.cart.model.CartMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartMappingRepository extends JpaRepository<CartMapping, Long> {
    CartMapping findCartMappingByCustomerUUID(UUID customerUUID);
}
