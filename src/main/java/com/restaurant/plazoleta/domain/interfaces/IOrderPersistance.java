package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.*;
import jdk.jshell.Snippet;

public interface IOrderPersistance {
    void registerOrder(Order order, Restaurant restaurant);
    PaginGeneric<OrderResponse> getOrdersAtRestaurantAnStatus(Integer page, Integer size, Integer restId, OrderStatus status);
}