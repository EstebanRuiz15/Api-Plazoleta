package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.interfaces.IDishPersistance;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.CategoryEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.ICategoryToEntityMapper;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.IDishMapperEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.IMapperRestaurantToEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.persistence.DishRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishPersistanceImpl implements IDishPersistance {
    private final DishRepositoryJpa repositoryJpa;
    private final IDishMapperEntity mapperDish;
    private final ICategoryToEntityMapper categoryMapper;
    private final IMapperRestaurantToEntity restaurantMapper;

    @Override
    public void saveDish(Dish dish, Restaurant restaurant, Category category) {
        DishEntity dishh = mapperDish.toEntity(dish);
        dishh.setCreatedAt(LocalDateTime.now());
        CategoryEntity cat=categoryMapper.toEntity(category);
        RestaurantEntity rest=restaurantMapper.toEntity(restaurant);
        dishh.setCategory(cat);
        dishh.setRestaurant(rest);
        repositoryJpa.save(dishh);
    }

    @Override
    public boolean existFindById(Integer id) {
        Optional<DishEntity> exist = repositoryJpa.findById(id);
        if(exist.isEmpty()) return false;
        return true;
    }

    @Override
    public void updateDish(Dish request, Integer id) {
        DishEntity dish= repositoryJpa.findById(id).get();
        if(!request.getName().isEmpty()){
            dish.setName(request.getName());
        }
        if(!request.getDescription().isEmpty()){
            dish.setDescription(request.getDescription());
        }
        if(request.getPrice() != null){
            dish.setPrice(request.getPrice());
        }
        repositoryJpa.save(dish);
    }

    @Override
    public Dish findById(Integer id) {
        Optional<DishEntity> dish=repositoryJpa.findById(id);
        return dish.map(mapperDish :: toDish)
                .orElse(null);
    }

    @Override
    public void setEnableAndDisable(Integer id, Boolean bol) {
        DishEntity dis= repositoryJpa.findById(id).get();
        dis.setActive(bol);
        repositoryJpa.save(dis);
    }
}
