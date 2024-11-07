package com.restaurant.plazoleta.infraestructur.driving_http.controllers;

import com.restaurant.plazoleta.domain.interfaces.IDishService;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishModifyDto;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IDishRequestMapper;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Dish")
public class DishController {
    private final IDishService service;
    private final IDishRequestMapper mapperRequest;

    @Operation(
            summary = "Create a new Dish",
            description = "This endpoint allows you to create a new dish in the restaurant system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string", example = InfraConstants.DISH_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object", example = "{\"errors\": [\"Field 'name' cannot be empty.\", \"Price must be a positive number.\"]}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category or Restaurant not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object", example = "{\"error\": \"Category not found\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while creating the dish.\"}")
                            )
                    )
            }
    )
    @PostMapping("/")
    public ResponseEntity<String> createNewDish(@Valid @RequestBody DishRequestDto request){
        service.createDish(mapperRequest.toDish(request));
        return ResponseEntity.ok(InfraConstants.DISH_SUCCES);
    }

    @Operation(
            summary = "Modify an existing Dish",
            description = "This endpoint allows you to modify an existing dish in the restaurant system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish successfully updated",
                            content = @Content(
                                    schema = @Schema(type = "string", example = InfraConstants.DIS_UPDATE_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema( example = "{\"errors\": [\"Field 'name' cannot be empty.\", \"Price must be a positive number.\"]}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dish not found",
                            content = @Content(
                                    schema = @Schema( example = "{\"error\": \"Dish not found\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(example = "{\"error\": \"Unexpected error occurred while updating the dish.\"}")
                            )
                    )
            }
    )
    @PatchMapping ("/update/{id}")
    public ResponseEntity<String> modifyDish(@RequestBody DishModifyDto request,
                                             @PathVariable Integer id){
        service.modifyDish(mapperRequest.toDishModify(request), id);
        return ResponseEntity.ok(InfraConstants.DIS_UPDATE_SUCCES);
    }
}
