package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IMapperRestaurantToEntity {
    @Mapping(target = "owner", ignore = true)
    RestaurantEntity toEntity (Restaurant restaurant);
    Restaurant toRestaurant (RestaurantEntity restaurant);
    @Mapping(source = "content", target = "items")
    @Mapping(source = "number", target = "currentPage")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "totalPages", target = "totalpages")
    @Mapping(source = "totalElements", target = "totalData")
    PaginGeneric<Restaurant> toPaginRestaurant(Page<RestaurantEntity> page);


}
