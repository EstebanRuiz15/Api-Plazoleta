package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDishRepositoryJpa extends JpaRepository<OrderDishEntity, Long>{

}
