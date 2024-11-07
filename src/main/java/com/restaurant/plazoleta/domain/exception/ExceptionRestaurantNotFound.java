package com.restaurant.plazoleta.domain.exception;

public class ExceptionRestaurantNotFound extends RuntimeException{
    public ExceptionRestaurantNotFound(String message) {
        super(message);
    }
}