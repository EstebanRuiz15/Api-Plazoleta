package com.restaurant.plazoleta.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer id;
    private Integer customer;
    private Integer chef;
    private OrderStatus status;
    private LocalDateTime date;
    private List<OrderDish> orderDishes;
    private Integer restaurantId;

    public Order() {
    }

    public Order(Integer customer, Integer chef, OrderStatus status, LocalDateTime date, List<OrderDish> orderDishes,Integer restaurantId) {
        this.customer = customer;
        this.chef = chef;
        this.status = status;
        this.date = date;
        this.orderDishes = orderDishes;
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getChef() {
        return chef;
    }

    public void setChef(Integer chef) {
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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
