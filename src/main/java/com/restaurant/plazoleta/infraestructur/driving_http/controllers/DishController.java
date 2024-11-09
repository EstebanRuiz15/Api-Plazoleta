package com.restaurant.plazoleta.infraestructur.driving_http.controllers;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ExceptionCategoryNotFound;
import com.restaurant.plazoleta.domain.interfaces.IDishService;
import com.restaurant.plazoleta.domain.model.DishResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishModifyDto;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IDishRequestMapper;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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

    @Operation(
            summary = "Disable a Dish",
            description = "This endpoint allows you to disable a dish in the restaurant system. The user must be the owner of the restaurant to perform this action.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish successfully disabled",
                            content = @Content(
                                    schema = @Schema(type = "string", example = InfraConstants.DISH_DISABLE_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema( example = "{\"errors\": [\"Dish ID must be provided.\"]}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dish ",
                            content = @Content(
                                    schema = @Schema( example = "{\"error\": \"Dish not found\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User is not the owner of the restaurant",
                            content = @Content(
                                    schema = @Schema( example = "{\"error\": \"User is not the owner of this restaurant\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(example = "{\"error\": \"Unexpected error occurred while disabling the dish.\"}")
                            )
                    )
            }
    )
    @PatchMapping("/disable{id}")
    public ResponseEntity<String> disableDish(@RequestParam Integer id){
        service.disableDish(id);
        return ResponseEntity.ok(InfraConstants.DISH_DISABLE_SUCCES);
    }

    @Operation(
            summary = "Enable a Dish",
            description = "This endpoint allows you to enable a dish in the restaurant system. The user must be the owner of the restaurant to perform this action.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish successfully enabled",
                            content = @Content(
                                    schema = @Schema(type = "string", example = InfraConstants.DISH_ENABLE_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema( example = "{\"errors\": [\"Dish ID must be provided.\"]}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dish or Restaurant not found",
                            content = @Content(
                                    schema = @Schema( example = "{\"error\": \"Dish not found\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User is not the owner of the restaurant",
                            content = @Content(
                                    schema = @Schema( example = "{\"error\": \"User is not the owner of this restaurant\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(example = "{\"error\": \"Unexpected error occurred while enabling the dish.\"}")
                            )
                    )
            }
    )
    @PatchMapping("/enable{id}")
    public ResponseEntity<String> enableDish(@RequestParam Integer id){
        service.enableDish(id);
        return ResponseEntity.ok(InfraConstants.DISH_ENABLE_SUCCES);
    }

    @Operation(
            summary = "Get all Dish",
            description = "This endpoint retrieves all dish with pagination. It also allows you to filter by category Any logged-in user can access this endpoint\n\n " +
                    "If the user is not authenticated, an 'Unauthorized' error will be returned.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the list of dish",
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category Not Found",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionCategoryNotFound.class)
                            )
                    )
            }
    )
    @GetMapping("/getAllDish")
    public ResponseEntity<PaginGeneric<DishResponse>> getAllDish(
            @RequestParam (defaultValue = InfraConstants.ONE) Integer page,
            @RequestParam (defaultValue =InfraConstants.TEN) Integer size,
            @RequestParam (defaultValue = "") String category){
        PaginGeneric<DishResponse> resp=(service.getAllDish(page, size, category));
        return ResponseEntity.ok(resp);
    }
}
