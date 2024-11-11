package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {
    Optional<RestaurantEntity> findByOwner(int ownerId);
}
