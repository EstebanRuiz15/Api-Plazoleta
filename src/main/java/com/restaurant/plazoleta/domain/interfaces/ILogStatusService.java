package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Order;
import com.restaurant.plazoleta.domain.model.OrderStatus;

public interface ILogStatusService {
    void registerStar(Order order, String clientEmail);
    void registerChange(OrderStatus status, Integer orderId, String employeEmail, Integer employeId);
}
