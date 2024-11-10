package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepositoryJpa extends JpaRepository<DishEntity, Integer> {
    Page<DishEntity> findByCategoryNameContainingIgnoreCaseAndActiveTrueAndRestaurantId(String categoryName,  Pageable pageable,Integer restaurantId);
    Page<DishEntity> findByActiveTrueAndRestaurantId( Pageable pageable,Integer restaurantId);
    List<DishEntity> findByActiveTrueAndRestaurantId( Integer restaurantId);

}
