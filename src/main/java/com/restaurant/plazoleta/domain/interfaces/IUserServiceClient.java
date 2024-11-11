package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.User;
import org.springframework.web.bind.annotation.RequestParam;

public interface IUserServiceClient {
    User GetUser( Integer idUser);
    User getEmploye();
    User getChefAtRestaurant( Integer restId);

}
