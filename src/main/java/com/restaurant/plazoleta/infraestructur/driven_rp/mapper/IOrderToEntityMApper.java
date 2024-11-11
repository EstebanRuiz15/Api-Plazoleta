package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.Order;
import com.restaurant.plazoleta.domain.model.OrderResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.context.annotation.Primary;

@Primary
@DecoratedWith(OrderMapperDecorator.class)
@Mapper(componentModel = "spring", uses = {IOrderDishToEntityMapper.class})
public interface IOrderToEntityMApper {

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "orderDishes", ignore = true)
    OrderEntity toEntity(Order order);

    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "orderDishes", ignore = true)
    Order toOrder(OrderEntity entity);

    @Mapping(source = "content", target = "items")
    @Mapping(source = "number", target = "currentPage")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "totalPages", target = "totalpages")
    @Mapping(source = "totalElements", target = "totalData")
    PaginGeneric<OrderResponse> toOrderPageable(Page<OrderEntity> page);

    @Mapping(target = "customer", expression = "java(getCustomerName(orderEntity.getCustomer()))")
    @Mapping(target = "chef", expression = "java(getChefName(orderEntity.getChef()))")
    OrderResponse toOrderResponse(OrderEntity orderEntity);

    String getCustomerName(Long customerId);

    String getChefName(Long chefId);

    String getUserName(Long userId);
}
