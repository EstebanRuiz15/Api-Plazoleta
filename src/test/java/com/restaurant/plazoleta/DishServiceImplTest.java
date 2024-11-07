package com.restaurant.plazoleta;

import static org.mockito.Mockito.*;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ExceptionCategoryNotFound;
import com.restaurant.plazoleta.domain.exception.ExceptionDishNotFound;
import com.restaurant.plazoleta.domain.exception.ExceptionRestaurantNotFound;
import com.restaurant.plazoleta.domain.interfaces.IDishPersistance;
import com.restaurant.plazoleta.domain.interfaces.ICategoriaPersistance;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.services.DishServiceImpl;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DishServiceImplTest {

    @Mock
    private IDishPersistance persistanceDish;
    @Mock
    private ICategoriaPersistance categoriaServices;
    @Mock
    private IRestaurantPersistance restaurantService;
    private DishServiceImpl dishService;
    private Dish dishh;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishService = new DishServiceImpl(persistanceDish, categoriaServices, restaurantService);
        dishh = new Dish();
        dishh.setName("Test Dish");
        dishh.setDescription("Test Description");
        dishh.setPrice(100.0);
        dishh.setCategory(1);
        dishh.setRestaurant(1);
    }

    @Test
    void testCreateDish_Success() {

        Dish dish = new Dish();
        dish.setCategory(1);
        dish.setRestaurant(1);

        Category category = new Category();
        category.setId(1);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);

        when(categoriaServices.finById(1)).thenReturn(category);
        when(restaurantService.findById(1)).thenReturn(restaurant);


        dishService.createDish(dish);

        verify(persistanceDish).saveDish(dish, restaurant, category);}
    @Test
    void testCreateDish_CategoryNotFound() {
        Dish dish = new Dish();
        dish.setCategory(1);
        dish.setRestaurant(1);

        when(categoriaServices.finById(1)).thenReturn(null);  // Act & Assert
        assertThrows(ExceptionCategoryNotFound.class, () -> dishService.createDish(dish), "Category not found");
    }

    @Test
    void testCreateDish_RestaurantNotFound() {

        Dish dish = new Dish();
        dish.setCategory(1);
        dish.setRestaurant(1);

        Category category = new Category();
        category.setId(1);

        when(categoriaServices.finById(1)).thenReturn(category);
        when(restaurantService.findById(1)).thenReturn(null); // Simula que el restaurante no existe

        assertThrows(ExceptionRestaurantNotFound.class, () -> dishService.createDish(dish), "Restaurant not found");
    }

    @Test
    void testCreateDish_CategoryNotFound_Message() {
        // Arrange
        Dish dish = new Dish();
        dish.setCategory(1);
        dish.setRestaurant(1);

        when(categoriaServices.finById(1)).thenReturn(null);
        ExceptionCategoryNotFound exception = assertThrows(ExceptionCategoryNotFound.class, () -> dishService.createDish(dish));
        assertEquals("Category not found1", exception.getMessage());
    }

    @Test
    void testCreateDish_RestaurantNotFound_Message() {

        Dish dish = new Dish();
        dish.setCategory(1);
        dish.setRestaurant(1);

        Category category = new Category();
        category.setId(1);

        when(categoriaServices.finById(1)).thenReturn(category);
        when(restaurantService.findById(1)).thenReturn(null);
        ExceptionRestaurantNotFound exception = assertThrows(ExceptionRestaurantNotFound.class, () -> dishService.createDish(dish));
        assertEquals("Restaurant not found1", exception.getMessage());
    }
    @Test
    void modifyDish_ShouldThrowException_WhenIdIsNull() {
        Exception exception = assertThrows(ExceptionDishNotFound.class, () -> {
            dishService.modifyDish(dishh, null);
        });

        assertEquals(ConstantsDomain.Dish_NOT_FOUND + null, exception.getMessage());
    }

    @Test
    void modifyDish_ShouldThrowException_WhenIdDoesNotExist() {
        when(persistanceDish.existFindById(1)).thenReturn(false);

        Exception exception = assertThrows(ExceptionDishNotFound.class, () -> {
            dishService.modifyDish(dishh, 1);
        });

        assertEquals(ConstantsDomain.Dish_NOT_FOUND + 1, exception.getMessage());
        verify(persistanceDish, never()).updateDish(any(Dish.class), anyInt());
    }

    @Test
    void modifyDish_ShouldThrowException_WhenAllFieldsAreNullOrEmpty() {
        when(persistanceDish.existFindById(1)).thenReturn(true);

        dishh.setName(null);
        dishh.setDescription(null);
        dishh.setPrice(null);

        Exception exception = assertThrows(ErrorExceptionParam.class, () -> {
            dishService.modifyDish(dishh, 1);
        });

        assertEquals(ConstantsDomain.NOT_ALL_FIELD_EMPTY, exception.getMessage());
        verify(persistanceDish, never()).updateDish(any(Dish.class), anyInt());
    }

    @Test
    void modifyDish_ShouldUpdateDishSuccessfully() {
        when(persistanceDish.existFindById(1)).thenReturn(true);

        dishService.modifyDish(dishh, 1);

        verify(persistanceDish, times(1)).updateDish(dishh, 1);
    }
}