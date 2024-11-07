package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.model.User;

public interface  IRestaurantPersistance {
   void saveRestaurant(Restaurant restaurant, User user);
   Restaurant findById(Integer id);
}
