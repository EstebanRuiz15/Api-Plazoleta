package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.OrderResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.OrderDishResponseDto;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.OrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOrderDishResponseMapper.class})
public interface IOrderResponseDtoMapper {
    PaginGeneric<OrderResponseDto> toPageableResponseDto(PaginGeneric<OrderResponse> response);
    OrderResponseDto toResponseDto(OrderResponse response);
}
