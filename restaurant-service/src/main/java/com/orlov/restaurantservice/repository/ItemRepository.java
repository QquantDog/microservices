package com.orlov.restaurantservice.repository;

import com.orlov.restaurantservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> getItemByItemCode(String itemCode);

    @Query("""
                select i from Item i
                    join i.menuItems mi
                    join mi.menu m
                    join m.restaurant r
                    where m.id = :menuId
                        and i.itemCode = :itemCode
                        and r.restaurantCode = :restaurantCode
""")
    Optional<Item> findItemByItemCodeAndMenuIdForRestaurant(String restaurantCode, Long menuId, String itemCode);
}
