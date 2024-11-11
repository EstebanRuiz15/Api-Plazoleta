package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.exception.ErrorFeignException;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.User;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.feign.UserClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignServeImpl implements IUserServiceClient {
    private final UserClient userClient;

    @Override
    public User GetUser(Integer idUser) {
        try {
            return userClient.getUserById(idUser);
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE) + e);
        }
    }

    @Override
    public User getEmploye() {
        try {
            return userClient.getEmploye();
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE) + e);
        }
    }

    @Override
    public User getChefAtRestaurant(Integer restId) {
        try {
            return userClient.getChefAtRestaurant(restId);
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE) + e);
        }
    }
}