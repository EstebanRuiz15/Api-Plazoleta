package com.restaurant.plazoleta.infraestructur.driving_http.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDishResponseDto {
    private Integer id;
    private DishResponseDto dish;
    private int quantity;
}
