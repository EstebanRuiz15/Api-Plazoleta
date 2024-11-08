package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IMapperRestaurantToEntity {
    @Mapping(target = "owner", ignore = true)
    RestaurantEntity toEntity (Restaurant restaurant);
    Restaurant toRestaurant (RestaurantEntity restaurant);


}
