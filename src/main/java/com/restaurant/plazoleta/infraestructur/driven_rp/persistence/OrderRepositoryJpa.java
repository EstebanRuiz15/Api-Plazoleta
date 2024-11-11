package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.domain.model.OrderStatus;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface OrderRepositoryJpa extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT o FROM OrderEntity o WHERE o.restaurant.id = :restaurantId AND o.status = :status")
    Page<OrderEntity> findByRestaurantIdAndStatus(Pageable pageable, @Param("restaurantId") Long restaurantId, @Param("status") OrderStatus status);
    Optional<OrderEntity> findBySecurityPin(String securityPin);
}
