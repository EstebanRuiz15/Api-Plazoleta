package com.restaurant.plazoleta.infraestructur.driving_http.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DishModifyDto {

    private String name;
    private  Double price;
    private String description;
}
