package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionUserInvalid;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantService;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.model.User;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;

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

        persistance.saveRestaurant(request, user);
    }
}
