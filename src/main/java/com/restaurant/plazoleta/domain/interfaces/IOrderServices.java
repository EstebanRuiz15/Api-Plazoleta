package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Order;
import com.restaurant.plazoleta.domain.model.OrderResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;

public interface IOrderServices {
    void registerOrder(Order order);
    PaginGeneric<OrderResponse> getOrdersAtRestaurant(Integer page, Integer size, String status);

}
