package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ErrorExceptionUserInvalid;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantService;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.model.User;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;

import java.security.CodeSigner;

public class RestaurantServiceImpl implements IRestaurantService {
    private final IRestaurantPersistance persistance;
    private final IUserServiceClient userClient;

    public RestaurantServiceImpl(IRestaurantPersistance persistance,IUserServiceClient userClient) {
        this.persistance = persistance;
        this.userClient=userClient;
    }

    @Override
    public void saveRestaurant(Restaurant request) {
        User user=userClient.GetUser(request.getOwner());
        if(user == null)
            throw new ErrorExceptionUserInvalid(ConstantsDomain.ERROR_USER +
                    request.getOwner()+ConstantsDomain.NOT_EXIST);

        if(!user.getRol().equalsIgnoreCase(ConstantsDomain.OWNER)){
            throw new ErrorExceptionUserInvalid(ConstantsDomain.ERROR_USER
                    +user.getName()+ConstantsDomain.NOT_HAVE_OWNER_ROL);
        }
        if(persistance.findByOwner(request.getOwner()) != null)
            throw new ErrorExceptionParam(ConstantsDomain.ALREADY_RESTAURANT_WITH_OWNER);

        persistance.saveRestaurant(request, user);
    }

    @Override
    public PaginGeneric<Restaurant> getAllRestaurants(Integer page, Integer size) {
        if(page<1 || size <1)throw  new ErrorExceptionParam(ConstantsDomain.PAGE_OR_SIZE_ERROR);
        return persistance.getAllRestaurants(page,size);
    }

    @Override
    public Restaurant findById(Integer id) {
        return persistance.findById(id);
    }

    @Override
    public Integer getRestaurantOwnerId(Integer idOwner) {
     return persistance.findByOwner(idOwner).getOwner();
    }
}
