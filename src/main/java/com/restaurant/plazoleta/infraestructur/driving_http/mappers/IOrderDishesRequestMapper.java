package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.OrderDish;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.OrderDishDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderDishesRequestMapper {
    OrderDish toOrderDish(OrderDishDto dishDto);
    List<OrderDish>toListOrderDishes(List<OrderDishDto > dishesDto);
}
