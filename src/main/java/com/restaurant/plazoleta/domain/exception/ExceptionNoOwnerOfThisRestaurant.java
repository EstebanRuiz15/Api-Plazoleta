package com.restaurant.plazoleta.domain.exception;

public class ExceptionNoOwnerOfThisRestaurant extends RuntimeException{
    public ExceptionNoOwnerOfThisRestaurant(String message) {
        super(message);
    }
}
