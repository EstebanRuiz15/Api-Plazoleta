package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.RestaurantRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRestauratRequestMapper {
    Restaurant toRestaurant (RestaurantRequestDto request);

}
