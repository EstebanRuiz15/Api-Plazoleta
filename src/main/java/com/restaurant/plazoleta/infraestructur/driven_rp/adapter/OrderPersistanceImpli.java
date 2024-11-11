package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.interfaces.IOrderPersistance;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.*;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderDishEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.IMapperRestaurantToEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.IOrderDishToEntityMapper;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.IOrderToEntityMApper;
import com.restaurant.plazoleta.infraestructur.driven_rp.persistence.OrderDishRepositoryJpa;
import com.restaurant.plazoleta.infraestructur.driven_rp.persistence.OrderRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPersistanceImpli implements IOrderPersistance {
    private final IOrderDishToEntityMapper mapperOrderDish;
    private final IOrderToEntityMApper mapperOrder;
    private final OrderRepositoryJpa repository;
    private final IMapperRestaurantToEntity restMapper;
    private final OrderDishRepositoryJpa repositoryOrderDish;
    private final IUserServiceClient feignClient;

    @Override
    public void registerOrder(Order order, Restaurant restaurant) {
        List<OrderDishEntity> orderDish = mapperOrderDish.toListEntity(order.getOrderDishes());
        OrderEntity orderEntity = mapperOrder.toEntity(order);
        orderEntity.setRestaurant(restMapper.toEntity(restaurant));
        orderEntity.setDate(LocalDateTime.now());
        orderEntity.setStatus(OrderStatus.PENDING);

        orderEntity = repository.save(orderEntity);
        for (OrderDishEntity orderDishEntity : orderDish) {
            orderDishEntity.setOrder(orderEntity);

        }
        repositoryOrderDish.saveAll(orderDish);
    }

    @Override
    public PaginGeneric<OrderResponse> getOrdersAtRestaurantAnStatus(Integer page, Integer size, Integer restId, OrderStatus status) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("status")));
        Page<OrderEntity> orderPage = repository.findByRestaurantIdAndStatus(pageable, restId.longValue(), status);
        return mapperOrder.toOrderPageable(orderPage);
    }

    @Override
    public void assigned_employee_id(Integer employeId, Integer orderId) {
        OrderEntity order=repository.findById(orderId.longValue()).get();
        order.setAssigned_employee_id(employeId.longValue());
        order.setStatus(OrderStatus.IN_PREPARATION);
        repository.save(order);
    }

    @Override
    public Order findById(Integer id) {
        Optional<OrderEntity> order=repository.findById(id.longValue());
        return order.map(mapperOrder :: toOrder)
                .orElse(null);
    }
}
