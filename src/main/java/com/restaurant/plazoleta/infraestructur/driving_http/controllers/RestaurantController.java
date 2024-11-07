package com.restaurant.plazoleta.infraestructur.driving_http.controllers;

import com.restaurant.plazoleta.domain.interfaces.IRestaurantService;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.RestaurantRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IRestauratRequestMapper;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final IRestaurantService restaurantService;
    private final IRestauratRequestMapper restaurantMapper;

    @Operation(
            summary = "Create a new restaurant",
            description = "This endpoint creates a new restaurant based on the provided information in the request body.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Restaurant created successfully",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request due to validation errors",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request due to invalid user data (owner validation failed)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error due to unexpected server issues",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/")
    public ResponseEntity<String> createRestaurant(
            @Valid @RequestBody @Parameter(description = InfraConstants.DETAIL)RestaurantRequestDto request){
        restaurantService.saveRestaurant(restaurantMapper.toRestaurant(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(InfraConstants.CREATE_RESTAURANT_SUCCES);

    }

}
