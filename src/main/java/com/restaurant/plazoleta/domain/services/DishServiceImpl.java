package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.*;
import com.restaurant.plazoleta.domain.interfaces.*;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import org.apache.tomcat.util.bcel.Const;

public class DishServiceImpl implements IDishService {
    private final IDishPersistance persistanceDish;
    private final ICategoriaPersistance categoriaServices;
    private final IRestaurantPersistance restaurantService;
    private final IValidateAutorizeFeign feignClient;

    public DishServiceImpl(IDishPersistance persistanceDish, ICategoriaPersistance categoriaServices, IRestaurantPersistance restaurantService, IValidateAutorizeFeign feignClient) {
        this.persistanceDish = persistanceDish;
        this.categoriaServices = categoriaServices;
        this.restaurantService = restaurantService;
        this.feignClient = feignClient;
    }

    @Override
    public void createDish(Dish dish) {
        Category category=categoriaServices.finById(dish.getCategory());
        if(category == null)
            throw new ExceptionCategoryNotFound(ConstantsDomain.CATEGORY_NOT_FOUND +dish.getCategory());

        Restaurant restaurant= restaurantService.findById(dish.getRestaurant());
        if(restaurant == null )
            throw new ExceptionRestaurantNotFound(ConstantsDomain.RESTAURANT_NOT_FOUND+dish.getRestaurant());
        Integer idOwner=feignClient.getUserId();
        if(idOwner != restaurant.getOwner()){
            throw new ExceptionNoOwnerOfThisRestaurant(ConstantsDomain.NO_OWNER_THIS_REST);
        }
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
        Integer idOwner=feignClient.getUserId();
        Dish dis= persistanceDish.findById(id);
        if(idOwner != dis.getRestaurant()){
            throw new ExceptionNoOwnerOfThisRestaurant(ConstantsDomain.NO_OWNER_THIS_REST);
        }
        persistanceDish.updateDish(dish,id);

    }

    @Override
    public void disableDish(Integer id) {

       Dish dish=validateDisableAndEnableDish(id);
       if(!dish.getActive()){
           throw new ExceptionEnableAndDisableDish(ConstantsDomain.ALREADY_DISABLE);
        }
       persistanceDish.setEnableAndDisable(id, Boolean.FALSE);
    }

    @Override
    public void enableDish(Integer id) {
        Dish dish=validateDisableAndEnableDish(id);
        if(dish.getActive()){
            throw new ExceptionEnableAndDisableDish(ConstantsDomain.ALREADY_ENABLE);
        }
        persistanceDish.setEnableAndDisable(id, Boolean.TRUE);
    }

    private Dish validateDisableAndEnableDish(Integer id){
        Dish dish=persistanceDish.findById(id);
        if(dish == null)
            throw  new ExceptionDishNotFound(ConstantsDomain.Dish_NOT_FOUND+id);

        Restaurant restau= restaurantService.findById(dish.getRestaurant());
        if(restau == null)
            throw new ExceptionRestaurantNotFound(ConstantsDomain.RESTAURANT_NOT_FOUND+dish.getRestaurant());

        Integer idOwner = feignClient.getUserId();
        if(idOwner != restau.getOwner()){
            throw new ExceptionNoOwnerOfThisRestaurant(ConstantsDomain.NO_OWNER_THIS_REST);
        }

        return dish;
    }
}
