package com.restaurant.plazoleta.infraestructur.driving_http.controllers;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantService;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.RestaurantRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.AllRestaurantsResponse;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IRestauratRequestMapper;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.RestaurantToDtoMapper;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final IRestaurantService restaurantService;
    private final IRestauratRequestMapper restaurantMapper;
    private final RestaurantToDtoMapper responseMapper;

    @Operation(
            summary = "Create a new restaurant",
            description = "This endpoint creates a new restaurant based on the provided information in the request body.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Restaurant created successfully",
                            content = @Content(
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request due to validation errors",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request due to invalid user data (owner validation failed)",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error due to unexpected server issues",
                            content = @Content(
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

    @Operation(
            summary = "Get all restaurants",
            description = "This endpoint retrieves all restaurants with pagination. Any logged-in user can access this endpoint\n\n " +
                    "If the user is not authenticated, an 'Unauthorized' error will be returned.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the list of restaurants",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginGeneric.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request due to invalid page or size parameters",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorExceptionParam.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized: The user is not authenticated",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorExceptionParam.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error due to unexpected server issues",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/getRestaurants{page}{size}")
    public ResponseEntity<PaginGeneric<AllRestaurantsResponse>> getAllRestaurants(
            @RequestParam (defaultValue = InfraConstants.ONE )Integer page,
            @RequestParam (defaultValue = InfraConstants.TEN)Integer size ){
        PaginGeneric<Restaurant> resta= restaurantService.getAllRestaurants(page, size);
        return ResponseEntity.ok(
                responseMapper.toPaginRestaurant(resta)
        );

    }

}
