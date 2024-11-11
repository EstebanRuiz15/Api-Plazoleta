package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.User;

public interface IUserServiceClient {
    User GetUser( Integer idUser);
    User getEmploye();

}
