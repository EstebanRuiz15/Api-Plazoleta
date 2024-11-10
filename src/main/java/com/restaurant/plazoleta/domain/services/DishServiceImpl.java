package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.*;
import com.restaurant.plazoleta.domain.interfaces.*;
import com.restaurant.plazoleta.domain.model.*;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;

import java.util.List;

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

    @Override
    public PaginGeneric<DishResponse> getAllDishAtRestaurant(Integer page, Integer size, String categoryFilter, Integer restId) {
        if(page<1 || size <1)throw  new ErrorExceptionParam(ConstantsDomain.PAGE_OR_SIZE_ERROR);
        if(restId == null) throw new ErrorExceptionParam(ConstantsDomain.REST_ID_NOT_NULL);
        Restaurant restaurant= restaurantService.findById(restId);
        if(restaurant==null) throw new ExceptionRestaurantNotFound(ConstantsDomain.RESTAURANT_NOT_FOUND+restId);
        if(categoryFilter == null || categoryFilter.isEmpty()){
            return persistanceDish.getAllDishAtRestaurant(page, size, restId);
        }
        if(!categoriaServices.existsByName(categoryFilter.trim())){
            throw new ExceptionCategoryNotFound(ConstantsDomain.CATEGORY_NOT_FOUND+categoryFilter);
        }
        return persistanceDish.getAllDishWithFilterCategory(page, size, categoryFilter, restId);
    }

    @Override
    public List<Dish> getAllDishAtRestaurantActive(Integer restaurantId) {
        return persistanceDish.getAllDishAtRestaurantActive(restaurantId);
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
