package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.Order;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.OrderRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = IOrderDishesRequestMapper.class)
public interface  IOrderRequestMapper {
    Order toOrder(OrderRequestDto request);
}

