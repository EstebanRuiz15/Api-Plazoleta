package com.restaurant.plazoleta.domain.exception;

public class ExceptionDishNotFound extends RuntimeException{
    public ExceptionDishNotFound(String message) {
        super(message);
    }
}
