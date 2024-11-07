package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.interfaces.ICategoriaPersistance;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.CategoryEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.mapper.ICategoryToEntityMapper;
import com.restaurant.plazoleta.infraestructur.driven_rp.persistence.CategoryRespositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryPersistanceImpl implements ICategoriaPersistance {
    private final CategoryRespositoryJpa respositoryJpa;
    private final ICategoryToEntityMapper mapper;

    @Override
    public void save(String name) {
        Category category=new Category(name);
        respositoryJpa.save(mapper.toEntity(category));
    }

    @Override
    public Category finById(int id) {
        Optional< CategoryEntity> category= respositoryJpa.findById(id);
        return category.map(mapper::toCategory)
                .orElse(null);
    }

    @Override
    public Category findByName(String name) {
        Optional<CategoryEntity> category=respositoryJpa.findByNameIgnoreCase(name);
        return category.map(mapper::toCategory)
                .orElse(null);
    }
}
