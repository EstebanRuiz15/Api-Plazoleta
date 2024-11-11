package com.restaurant.plazoleta.aplication.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.plazoleta.domain.exception.*;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControlAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ConstantsDomain.ERROR_VALIDATION,
                errors
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorExceptionParam.class)
    public ResponseEntity<?> resourceNotFoundException(ErrorExceptionParam ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorExceptionUserInvalid.class)
    public ResponseEntity<?> resourceNotFoundException(ErrorExceptionUserInvalid ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                details
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ErrorFeignException.class)
    public ResponseEntity<?> handleErrorFeignException(ErrorFeignException ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Service Unavailable",
                details
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ExceptionCategory.class)
    public ResponseEntity<?> handlerCategoryException(ExceptionCategory ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionCategoryNotFound.class)
    public ResponseEntity<?> resourceNotFoundException(ExceptionCategoryNotFound ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExceptionRestaurantNotFound.class)
    public ResponseEntity<?> resourceNotFoundException(ExceptionRestaurantNotFound ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExceptionDishNotFound.class)
    public ResponseEntity<?> resourceNotFoundException(ExceptionDishNotFound ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                "Error in fields",
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExceptionNoOwnerOfThisRestaurant.class)
    public ResponseEntity<?> ExceptionNoOwnerRestaurant(ExceptionNoOwnerOfThisRestaurant ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExceptionEnableAndDisableDish.class)
    public ResponseEntity<?> ExceptionEnableAndDisable(ExceptionEnableAndDisableDish ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return new ResponseEntity<>("Missing required parameter: " +ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionNotFoundUser.class)
    public ResponseEntity<?> resourceNotFoundException(ExceptionNotFoundUser ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                "Error in fields",
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExceptionOrderNotFound.class)
    public ResponseEntity<?> orderNotFoundException(ExceptionOrderNotFound ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorExceptionConflict.class)
    public ResponseEntity<?> resourceNotFoundException(ErrorExceptionConflict ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put(InfraConstants.ERROR, ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

}