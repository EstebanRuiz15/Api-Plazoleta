package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.model.User;

import java.util.List;

public interface  IRestaurantPersistance {
   void saveRestaurant(Restaurant restaurant, User user);
   Restaurant findById(Integer id);
   PaginGeneric<Restaurant> getAllRestaurants(Integer page, Integer size);
   List<Restaurant> findByOwner(int ownerId);


}
