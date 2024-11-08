package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Dish;

public interface IDishService {
    void createDish(Dish dish);
    void modifyDish(Dish dish, Integer id);
    void disableDish(Integer id);
    void enableDish(Integer id);

}
