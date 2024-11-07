package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepositoryJpa extends JpaRepository<DishEntity, Integer> {
}
