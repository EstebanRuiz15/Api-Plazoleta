package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.model.User;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.IMapperRestaurantToEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.persistence.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantPersistanceImpl implements IRestaurantPersistance {
    private final IMapperRestaurantToEntity mapperEntity;
    private final RestaurantJpaRepository repositoryJpa;

    @Override
    public void saveRestaurant(Restaurant restaurant, User user) {
        RestaurantEntity entity= mapperEntity.toEntity(restaurant);
        entity.setOwner(restaurant.getOwner());
        repositoryJpa.save(entity);
    }

    @Override
    public Restaurant findById(Integer id) {
        Optional<RestaurantEntity> restaurant=repositoryJpa.findById(id);
        return restaurant.map(mapperEntity :: toRestaurant)
                .orElse(null);
    }
}
