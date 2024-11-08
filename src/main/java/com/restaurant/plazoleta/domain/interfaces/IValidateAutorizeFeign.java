package com.restaurant.plazoleta.domain.interfaces;

public interface IValidateAutorizeFeign {
    Boolean validateAdmin();
    Boolean validateOWNER();
    Boolean validateCient();
    Boolean validateEmployee();
    Boolean validateToken();
}
