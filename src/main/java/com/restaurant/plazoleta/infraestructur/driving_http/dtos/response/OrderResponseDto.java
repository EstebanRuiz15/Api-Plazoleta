package com.restaurant.plazoleta.infraestructur.driving_http.dtos.response;

import com.restaurant.plazoleta.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponseDto {
    private Integer id;
    private String customer;
    private String chef;
    private OrderStatus status;
    private LocalDateTime date;
    private List<OrderDishResponseDto> orderDishes;
    private Integer assigned_employee_id;

}
