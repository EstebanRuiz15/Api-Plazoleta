package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepositoryJpa extends JpaRepository<DishEntity, Integer> {
    Page<DishEntity> findByCategoryNameContainingIgnoreCaseAndActiveTrue(String categoryName, Pageable pageable);
    Page<DishEntity> findByActiveTrue(Pageable pageable);
}
