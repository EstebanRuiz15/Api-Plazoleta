package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDishRequestMapper {
    @Mapping(target = "createdAt", ignore = true)
    Dish toDish(DishRequestDto request);
}
