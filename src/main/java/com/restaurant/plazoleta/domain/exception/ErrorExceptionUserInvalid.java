package com.restaurant.plazoleta.domain.exception;

public class ErrorExceptionUserInvalid extends RuntimeException{
    public ErrorExceptionUserInvalid(String message) {
        super(message);
    }
}
