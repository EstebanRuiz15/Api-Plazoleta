package com.restaurant.plazoleta.domain.model;


import java.time.LocalDateTime;

public class Dish {
    private String name;

    private Double price;

    private String description;

    private String imageUrl;

    private int restaurant;

    private int category;

    private LocalDateTime createdAt;

    public Dish() {
    }

    public Dish(String name, Double price, String description, String imageUrl,  int restaurant, int category, LocalDateTime createdAt) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.restaurant = restaurant;
        this.category = category;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
