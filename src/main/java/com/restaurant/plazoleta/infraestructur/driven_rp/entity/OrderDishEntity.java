package com.restaurant.plazoleta.infraestructur.driven_rp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DishEntity dish;

    @Column(nullable = false)
    private int quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

}
