package com.restaurant.plazoleta.aplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExceptionResponse {
    private int statusCode;
    private String message;
    private Map<String, String> details;


    
    
}
