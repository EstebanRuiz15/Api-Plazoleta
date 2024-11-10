package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.*;

public interface IDishPersistance
{
    void saveDish(Dish dish, Restaurant restaurant, Category category);
    boolean existFindById(Integer id);
    void updateDish(Dish request, Integer id);
    Dish findById(Integer id);
    void setEnableAndDisable(Integer id, Boolean bol);
    PaginGeneric<DishResponse> getAllDishAtRestaurant(Integer page, Integer size, Integer restId);
    PaginGeneric<DishResponse> getAllDishWithFilterCategory(Integer page, Integer size, String categoryFilter,Integer restId);

}
