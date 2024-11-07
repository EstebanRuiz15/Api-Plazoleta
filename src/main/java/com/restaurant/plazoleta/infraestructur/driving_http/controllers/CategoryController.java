package com.restaurant.plazoleta.infraestructur.driving_http.controllers;

import com.restaurant.plazoleta.domain.interfaces.ICategoriaServices;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Category")
public class CategoryController {
    private final ICategoriaServices service;

    @Operation(
            summary = "Create a new Category",
            description = "This endpoint allows you to create a new category for dishes in the restaurant system.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string", example = ConstantsDomain.CREAT_SUCCESSFULL)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid or missing category name",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object", example = "{\"error\": \"Category name cannot be empty\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while creating the category\"}")
                            )
                    )
            }
    )
    @PostMapping ("/")
        public ResponseEntity<String> createCategory(@RequestParam String name){
        service.createCategory(name);
         return  ResponseEntity.status(HttpStatus.CREATED).body(ConstantsDomain.CREAT_SUCCESSFULL);
        }
}
