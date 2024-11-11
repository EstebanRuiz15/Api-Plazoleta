package com.restaurant.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishResponse {

    private String name;

    private Double price;

    private String description;

    private String imageUrl;

    private String category;

}
