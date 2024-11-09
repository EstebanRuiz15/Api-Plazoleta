package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.Category;

public interface ICategoriaPersistance {
    void save(String Name);
    Category finById(int id);
    Category findByName(String name);
    boolean existsByName(String name);
}
