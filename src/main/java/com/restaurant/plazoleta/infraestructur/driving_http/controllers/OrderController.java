package com.restaurant.plazoleta.infraestructur.driving_http.controllers;

import com.restaurant.plazoleta.domain.exception.ExceptionCategory;
import com.restaurant.plazoleta.domain.interfaces.IOrderServices;
import com.restaurant.plazoleta.domain.model.OrderResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.OrderRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.OrderResponseDto;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IOrderRequestMapper;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IOrderResponseDtoMapper;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final IOrderRequestMapper mapperRequest;
    private final IOrderServices orderServices;
    private final IOrderResponseDtoMapper orderResponseMapper;

    @Operation(
            summary = "Register a new order",
            description = "This endpoint allows clients to place new orders in the restaurant system. " +
                    "It validates if the customer and chef exist, and if the dishes are available in the specified restaurant.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order successfully registered",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string", example = InfraConstants.REGISTER_ORDER_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"errors\": [\"Field 'customer' must be a number.\", \"Field 'chef' must be a number.\"]}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer or Chef or Restaurant or Dish not found",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Customer not found\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while registering the order.\"}")
                            )
                    )
            }
    )
    @PostMapping("/")
    public ResponseEntity<String> registerOrder(@RequestBody @Valid OrderRequestDto request){
        orderServices.registerOrder(mapperRequest.toOrder(request));
        return ResponseEntity.ok(InfraConstants.REGISTER_ORDER_SUCCES);
    }

    @Operation(
            summary = "Get orders filtered by status",
            description = "This endpoint lists all orders filtered by status. Only employees can access this endpoint as it lists only the orders related to the restaurant to which the employee belongs.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Orders successfully retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginGeneric.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Invalid page or size parameter.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No orders found for the provided status",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"No orders found for the provided status.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while retrieving orders.\"}")
                            )
                    )
            }
    )
    @GetMapping("/getOrders")
    public ResponseEntity<PaginGeneric<OrderResponseDto>> getAllOrderForStatus(
            @RequestParam (defaultValue = InfraConstants.ONE) Integer page,
            @RequestParam (defaultValue =InfraConstants.TEN) Integer size,
            @RequestParam  String statusFilter
    ) {
        PaginGeneric<OrderResponse> response=orderServices.getOrdersAtRestaurant(page, size, statusFilter);
        return ResponseEntity.ok(orderResponseMapper.toPageableResponseDto(response));
    }

    @Operation(
            summary = "Assign an employee to an order",
            description = "This endpoint assigns an employee to an order. The employee making the request is assigned to the order, and the order status is changed to 'In Preparation'.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employee successfully assigned to the order",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string", example = InfraConstants.ASSIGNED_SUCCESFULL)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order ID must be a positive integer.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Order is already assigned to an employee",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order is already assigned to an employee.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while assigning the employee to the order.\"}")
                            )
                    )
            }
    )
    @PatchMapping("/assignedEmploye")
    public ResponseEntity<String> assignatedEmployeToOrder(@RequestParam Integer idOrder){
        orderServices.assigned_employee_id(idOrder);
        return ResponseEntity.ok(InfraConstants.ASSIGNED_SUCCESFULL);
    }

    @Operation(
            summary = "Mark an order as delivered",
            description = "This method allows marking an order as delivered.\n\n It validates the security pin provided by the customer, and changes the order's status to 'DELIVERED', only if the current status is 'READY'.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order successfully marked as delivered",
                            content = @Content(
                                    schema = @Schema(type = "string", example = InfraConstants.ORDER_DELIVERED_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error in the provided data",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Invalid or missing security pin.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found or invalid security pin",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order not found or invalid pin.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Order is already delivered",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order is already delivered.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Order is not ready for delivery",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order is not ready for delivery.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while marking the order as delivered.\"}")
                            )
                    )
            }
    )
    @PatchMapping("/delivered")
    public ResponseEntity<String> deliveredOrder(@RequestParam String securityPin){
        orderServices.deliveredOrder(securityPin);
        return ResponseEntity.ok(InfraConstants.ORDER_DELIVERED_SUCCES);
    }

    @Operation(
            summary = "Cancel an order",
            description = "This method allows the customer to cancel their order.\n\n" +
                    "The order will only be cancelled if its status is 'PENDING'. Otherwise, a message will indicate that the order is already in process and cannot be cancelled. If the status is 'PENDING', it will be changed to 'CANCELLED'.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order successfully cancelled",
                            content = @Content(
                                    schema = @Schema(type = "string", example = InfraConstants.ORDER_CANCELED_SUCCES)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"User not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Order is not in a cancellable state",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Order is not in a cancellable state.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    schema = @Schema(type = "object", example = "{\"error\": \"Unexpected error occurred while cancelling the order.\"}")
                            )
                    )
            }
    )
    @PatchMapping("/cancelled")
    public ResponseEntity<String> cancelledOrder(){
        orderServices.canceledOrder();
        return ResponseEntity.ok(InfraConstants.ORDER_CANCELED_SUCCES);
    }
}
