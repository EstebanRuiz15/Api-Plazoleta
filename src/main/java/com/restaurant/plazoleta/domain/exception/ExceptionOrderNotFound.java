package com.restaurant.plazoleta.domain.exception;

public class ExceptionOrderNotFound extends RuntimeException{
    public ExceptionOrderNotFound(String message) {
        super(message);
    }
}
