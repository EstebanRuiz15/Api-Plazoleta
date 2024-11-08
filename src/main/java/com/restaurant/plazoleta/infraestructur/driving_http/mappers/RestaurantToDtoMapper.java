package com.restaurant.plazoleta.infraestructur.driving_http.mappers;

import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.response.AllRestaurantsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantToDtoMapper {
    PaginGeneric<AllRestaurantsResponse> toPaginRestaurant(PaginGeneric<Restaurant> page);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "logoUrl", target = "logoUrl")
    AllRestaurantsResponse toRestaurantResponse(Restaurant restaurantEntity);
}
