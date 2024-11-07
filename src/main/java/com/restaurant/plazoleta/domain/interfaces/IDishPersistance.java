package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.Restaurant;

public interface IDishPersistance
{
    void saveDish(Dish dish, Restaurant restaurant, Category category);
}
