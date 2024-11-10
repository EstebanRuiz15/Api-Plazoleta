package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.DishResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;

public interface IDishService {
    void createDish(Dish dish);
    void modifyDish(Dish dish, Integer id);
    void disableDish(Integer id);
    void enableDish(Integer id);
    PaginGeneric<DishResponse> getAllDishAtRestaurant(Integer page, Integer size, String categoryFilter,Integer restId);

}
