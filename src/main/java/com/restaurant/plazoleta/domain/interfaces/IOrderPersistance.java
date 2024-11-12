package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.*;

public interface IOrderPersistance {
    void registerOrder(Order order, Restaurant restaurant, String securityPin, String clientEmail);
    PaginGeneric<OrderResponse> getOrdersAtRestaurantAnStatus(Integer page, Integer size, Integer restId, OrderStatus status);
    void assigned_employee_id(Integer employeId, Integer orderId);
    Order findById(Integer id);
    void deliveredOrder(Order order);
    Order findBySecurityPin(String pin);
    void canceledOrder(Integer orderId);
    Order findByCustomerAndStatus(Integer idCustomer, OrderStatus status);


}
