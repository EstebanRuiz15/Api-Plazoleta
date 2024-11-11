package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.DishResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishResponseDtoMapper {
    List<DishResponseDto> toListDishResponse(List<Dish> dish);
    DishResponseDto toDishResponse(Dish dish);
}
