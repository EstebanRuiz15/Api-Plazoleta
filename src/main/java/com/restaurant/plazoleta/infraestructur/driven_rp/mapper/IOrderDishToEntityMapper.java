package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.OrderDish;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IDishMapperEntity.class})
public interface IOrderDishToEntityMapper {
    @Mapping(target = "order", ignore = true)
    OrderDishEntity toEntity(OrderDish orderDish);
    @Mapping(target = "order", ignore = true)
    OrderDish toOrderDish(OrderDishEntity entity);
    @Mapping(target = "order", ignore = true)
    List<OrderDishEntity> toListEntity(List<OrderDish> orderDishList);


}
