package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.DishResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.CategoryEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.DishEntity;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IDishMapperEntity {
    @Mapping(target= "restaurant", ignore = true)
    @Mapping(target= "category", ignore = true)
    DishEntity toEntity(Dish dish);

    @Mapping(target= "category", ignore = true)
    @Mapping(source = "restaurant", target = "restaurant", qualifiedByName = "restaurantToRestaurantId")
    Dish toDish(DishEntity dish);

    @Named("restaurantToRestaurantId")
    default Integer restaurantToRestaurantId(RestaurantEntity restaurantEntity) {
        return restaurantEntity != null ? restaurantEntity.getId().intValue() : null;
    }

    @Mapping(source = "content", target = "items")
    @Mapping(source = "number", target = "currentPage")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "totalPages", target = "totalpages")
    @Mapping(source = "totalElements", target = "totalData")
    PaginGeneric<DishResponse> toPaginDish(Page<DishEntity> page);
    @Mapping(source = "category", target = "category", qualifiedByName = "categoryToCategoryName")
    DishResponse toDishResponse(DishEntity dish);

    @Named("categoryToCategoryName")
    default String categoryToCategoryName(CategoryEntity category) {
        return category != null ? category.getName() : null;
    }
}
