package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface IDishMapperEntity {
    @Mapping(target= "restaurant", ignore = true)
    @Mapping(target= "category", ignore = true)
    DishEntity toEntity(Dish dish);

    @Mapping(target= "category", ignore = true)
    @Mapping(source = "restaurant", target = "restaurant", qualifiedByName = "restaurantToRestaurantId")
    Dish toDish(DishEntity dish);

    @Named("restaurantToRestaurantId")
    default Integer restaurantToRestaurantId(RestaurantEntity restaurantEntity) {
        return restaurantEntity != null ? restaurantEntity.getId().intValue() : null;
    }
}
