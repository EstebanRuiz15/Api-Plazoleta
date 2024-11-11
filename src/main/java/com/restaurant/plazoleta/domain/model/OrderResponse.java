package com.restaurant.plazoleta.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Integer id;
    private String customer;
    private String chef;
    private OrderStatus status;
    private LocalDateTime date;
    private List<OrderDish> orderDishes;
    private Integer assigned_employee_id;
    private String securityPin;

    public OrderResponse(Integer id, String customer, String chef, OrderStatus status, LocalDateTime date, List<OrderDish> orderDishes) {
        this.id = id;
        this.customer = customer;
        this.chef = chef;
        this.status = status;
        this.date = date;
        this.orderDishes = orderDishes;
    }

    public OrderResponse() {
    }

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }

    public Integer getAssigned_employee_id() {
        return assigned_employee_id;
    }

    public void setAssigned_employee_id(Integer assigned_employee_id) {
        this.assigned_employee_id = assigned_employee_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }
}
