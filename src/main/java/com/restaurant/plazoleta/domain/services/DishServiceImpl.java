package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ExceptionCategoryNotFound;
import com.restaurant.plazoleta.domain.exception.ExceptionDishNotFound;
import com.restaurant.plazoleta.domain.exception.ExceptionRestaurantNotFound;
import com.restaurant.plazoleta.domain.interfaces.*;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;

public class DishServiceImpl implements IDishService {
    private final IDishPersistance persistanceDish;
    private final ICategoriaPersistance categoriaServices;
    private final IRestaurantPersistance restaurantService;

    public DishServiceImpl(IDishPersistance persistanceDish, ICategoriaPersistance categoriaServices, IRestaurantPersistance restaurantService) {
        this.persistanceDish = persistanceDish;
        this.categoriaServices = categoriaServices;
        this.restaurantService = restaurantService;
    }

    @Override
    public void createDish(Dish dish) {
        Category category=categoriaServices.finById(dish.getCategory());
        if(category == null)
            throw new ExceptionCategoryNotFound(ConstantsDomain.CATEGORY_NOT_FOUND +dish.getCategory());

        Restaurant restaurant= restaurantService.findById(dish.getRestaurant());
        if(restaurant == null )
            throw new ExceptionRestaurantNotFound(ConstantsDomain.RESTAURANT_NOT_FOUND+dish.getRestaurant());

        persistanceDish.saveDish(dish, restaurant, category);
    }

    @Override
    public void modifyDish(Dish dish, Integer id) {
        if(!persistanceDish.existFindById(id)|| id == null)
            throw  new ExceptionDishNotFound(ConstantsDomain.Dish_NOT_FOUND+id);
        if(dish.getPrice() == null &&
                (dish.getDescription() == null || dish.getDescription().trim().isEmpty()) &&
                (dish.getName() == null || dish.getName().trim().isEmpty()))
        {
            throw new ErrorExceptionParam(ConstantsDomain.NOT_ALL_FIELD_EMPTY);
        }

        persistanceDish.updateDish(dish,id);

    }
}
