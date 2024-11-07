package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface IDishMapperEntity {
    @Mapping(target= "restaurant", ignore = true)
    @Mapping(target= "category", ignore = true)
    DishEntity toEntity(Dish dish);

    @Mapping(target= "restaurant", ignore = true)
    @Mapping(target= "category", ignore = true)
    Dish toDish(DishEntity dish);
}
