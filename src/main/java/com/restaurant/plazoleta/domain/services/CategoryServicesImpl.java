package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ExceptionCategory;
import com.restaurant.plazoleta.domain.interfaces.ICategoriaPersistance;
import com.restaurant.plazoleta.domain.interfaces.ICategoriaServices;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;

public class CategoryServicesImpl implements ICategoriaServices {
    private final ICategoriaPersistance persistance;

    public CategoryServicesImpl(ICategoriaPersistance persistance) {
        this.persistance = persistance;
    }

    @Override
    public void createCategory(String name) {
        if(name.trim().isBlank()) throw  new ErrorExceptionParam(ConstantsDomain.NAME_EMPTY);

        Category category=persistance.findByName(name);
        if(category != null)
            throw new ExceptionCategory(ConstantsDomain.NAME_AL_READY_EXIST);
        persistance.save(name);
    }

    @Override
    public boolean existsByName(String name) {
        return persistance.existsByName(name);
    }
}
