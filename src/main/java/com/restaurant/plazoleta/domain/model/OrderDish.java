package com.restaurant.plazoleta.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderDish {
    private Integer id;
    private Integer dishId;
    private Dish dish;
    private int quantity;
    private Order order;

    public OrderDish() {
    }

    public OrderDish(Dish dish, int quantity, Order order,Integer dishId) {
        this.dish = dish;
        this.quantity = quantity;
        this.order = order;
        this.dishId=dishId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
