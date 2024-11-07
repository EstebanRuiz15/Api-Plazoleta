package com.restaurant.plazoleta.domain.exception;

public class ExceptionCategoryNotFound extends RuntimeException{
    public ExceptionCategoryNotFound(String message) {
        super(message);
    }
}