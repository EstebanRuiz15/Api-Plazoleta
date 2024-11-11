package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.domain.model.Restaurant;

public interface IRestaurantService {
    void saveRestaurant(Restaurant request);

    PaginGeneric<Restaurant> getAllRestaurants(Integer page, Integer size);

    Restaurant findById(Integer id);

    Integer getRestaurantOwnerId(Integer idOwner);
}