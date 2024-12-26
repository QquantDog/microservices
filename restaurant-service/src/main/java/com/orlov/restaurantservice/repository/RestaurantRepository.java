package com.orlov.restaurantservice.repository;

import com.orlov.restaurantservice.model.Menu;
import com.orlov.restaurantservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select r from Restaurant r left join fetch r.menuList m")
    List<Restaurant> getAllRestaurantsWithMenus();

    Optional<Restaurant> findRestaurantByRestaurantCode(String restaurantCode);

}
