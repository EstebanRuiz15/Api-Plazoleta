package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryToEntityMapper {
    CategoryEntity toEntity(Category category);
    Category toCategory(CategoryEntity category);
}
