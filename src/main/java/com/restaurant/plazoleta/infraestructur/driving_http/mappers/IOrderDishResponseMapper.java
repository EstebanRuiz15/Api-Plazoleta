package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.OrderDish;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.OrderDishResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IDishResponseDtoMapper.class})
public interface IOrderDishResponseMapper {
    List<OrderDishResponseDto> toListResponseDish(List<OrderDish> orderDishes);
    OrderDishResponseDto toOrderDishResponseDto(OrderDish orderDish);
}
