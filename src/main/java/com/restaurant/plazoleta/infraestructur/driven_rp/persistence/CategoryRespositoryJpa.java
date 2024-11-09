package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;


import com.restaurant.plazoleta.infraestructur.driven_rp.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRespositoryJpa extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByNameIgnoreCase(String name);
    boolean existsByName(String name);
}
