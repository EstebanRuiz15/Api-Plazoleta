package com.restaurant.plazoleta;

import static org.mockito.Mockito.*;

import com.restaurant.plazoleta.domain.exception.*;
import com.restaurant.plazoleta.domain.interfaces.IDishPersistance;
import com.restaurant.plazoleta.domain.interfaces.ICategoriaPersistance;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.interfaces.IValidateAutorizeFeign;
import com.restaurant.plazoleta.domain.model.*;
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
    @Mock
    private IValidateAutorizeFeign clientFeign;
    private DishServiceImpl dishService;
    private Dish dishh;
    private Restaurant restaurant;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishService = new DishServiceImpl(persistanceDish, categoriaServices, restaurantService, clientFeign);
        dishh = new Dish();
        dishh.setName("Test Dish");
        dishh.setDescription("Test Description");
        dishh.setPrice(100.0);
        dishh.setCategory(1);
        dishh.setRestaurant(1);
        dishh.setActive(true);

        restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setOwner(1);
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
    public void testDisableDishSuccess() {
        when(persistanceDish.findById(1)).thenReturn(dishh);
        when(restaurantService.findById(1)).thenReturn(restaurant);
        when(clientFeign.getUserId()).thenReturn(1);

        dishService.disableDish(1);

        verify(persistanceDish).setEnableAndDisable(1, false);
    }

    @Test
    public void testDisableDishAlreadyDisabled() {
        dishh.setActive(false);
        when(persistanceDish.findById(1)).thenReturn(dishh);
        when(restaurantService.findById(1)).thenReturn(restaurant);
        when(clientFeign.getUserId()).thenReturn(1);

        Exception exception = assertThrows(ExceptionEnableAndDisableDish.class, () -> {
            dishService.disableDish(1);
        });

        assertEquals(ConstantsDomain.ALREADY_DISABLE, exception.getMessage());
        verify(persistanceDish, never()).setEnableAndDisable(anyInt(), anyBoolean());
    }

    @Test
    public void testEnableDishSuccess() {
        dishh.setActive(false);
        when(persistanceDish.findById(1)).thenReturn(dishh);
        when(restaurantService.findById(1)).thenReturn(restaurant);
        when(clientFeign.getUserId()).thenReturn(1);

        dishService.enableDish(1);

        verify(persistanceDish).setEnableAndDisable(1, true);
    }

    @Test
    public void testEnableDishAlreadyEnabled() {
        when(persistanceDish.findById(1)).thenReturn(dishh);
        when(restaurantService.findById(1)).thenReturn(restaurant);
        when(clientFeign.getUserId()).thenReturn(1);

        Exception exception = assertThrows(ExceptionEnableAndDisableDish.class, () -> {
            dishService.enableDish(1);
        });

        assertEquals(ConstantsDomain.ALREADY_ENABLE, exception.getMessage());
        verify(persistanceDish, never()).setEnableAndDisable(anyInt(), anyBoolean());
    }
    @Test
    public void testValidateDisableAndEnableDishNotFound() {
        when(persistanceDish.findById(1)).thenReturn(null);

        Exception exception = assertThrows(ExceptionDishNotFound.class, () -> {
            dishService.disableDish(1);
        });

        assertEquals(ConstantsDomain.Dish_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    public void testValidateDisableAndEnableDishRestaurantNotFound() {
        when(persistanceDish.findById(1)).thenReturn(dishh);
        when(restaurantService.findById(1)).thenReturn(null);

        Exception exception = assertThrows(ExceptionRestaurantNotFound.class, () -> {
            dishService.disableDish(1);
        });

        assertEquals(ConstantsDomain.RESTAURANT_NOT_FOUND + "1", exception.getMessage());
    }

    @Test
    public void testValidateDisableAndEnableDishNotOwner() {
        when(persistanceDish.findById(1)).thenReturn(dishh);
        when(restaurantService.findById(1)).thenReturn(restaurant);
        when(clientFeign.getUserId()).thenReturn(2);

        Exception exception = assertThrows(ExceptionNoOwnerOfThisRestaurant.class, () -> {
            dishService.disableDish(1);
        });

        assertEquals(ConstantsDomain.NO_OWNER_THIS_REST, exception.getMessage());
    }

    @Test
    void getAllDish_WhenPageAndSizeAreValid_AndNoCategoryFilter_ReturnsPagedDishes() {
        int page = 1;
        int size = 10;
        int rest=1;
        PaginGeneric<DishResponse> expectedResponse = new PaginGeneric<>();
        when(persistanceDish.getAllDishAtRestaurant(page, size,rest)).thenReturn(expectedResponse);
        when(restaurantService.findById(rest)).thenReturn(restaurant);
        PaginGeneric<DishResponse> result = dishService.getAllDishAtRestaurant(page, size, null,rest);

        assertEquals(expectedResponse, result);
        verify(persistanceDish).getAllDishAtRestaurant(page, size,rest);
        verify(persistanceDish, never()).getAllDishWithFilterCategory(anyInt(), anyInt(), anyString(),anyInt());
    }

    @Test
    void getAllDish_WhenPageAndSizeAreValid_AndHasValidCategoryFilter_ReturnsFilteredDishes() {

        int page = 1;
        int size = 10;
        int rest=1;

        String category = "Italian";
        PaginGeneric<DishResponse> expectedResponse = new PaginGeneric<>();
        when(restaurantService.findById(rest)).thenReturn(restaurant);

        when(categoriaServices.existsByName(category.trim())).thenReturn(true);
        when(persistanceDish.getAllDishWithFilterCategory(page, size, category, rest))
                .thenReturn(expectedResponse);

        PaginGeneric<DishResponse> result = dishService.getAllDishAtRestaurant(page, size, category, rest);

        assertEquals(expectedResponse, result);
        verify(categoriaServices).existsByName(category.trim());
        verify(persistanceDish).getAllDishWithFilterCategory(page, size, category, rest);
        verify(persistanceDish, never()).getAllDishAtRestaurant(anyInt(), anyInt(),anyInt());
    }

    @Test
    void getAllDish_WhenPageIsLessThanOne_ThrowsErrorExceptionParam() {

        int page = 0;
        int size = 10;
        int rest=1;

        assertThrows(ErrorExceptionParam.class, () ->
                        dishService.getAllDishAtRestaurant(page, size, null,rest),
                ConstantsDomain.PAGE_OR_SIZE_ERROR
        );

        verify(persistanceDish, never()).getAllDishAtRestaurant(anyInt(), anyInt(),anyInt());
        verify(persistanceDish, never()).getAllDishWithFilterCategory(anyInt(), anyInt(), anyString(),anyInt());
    }

    @Test
    void getAllDish_WhenSizeIsLessThanOne_ThrowsErrorExceptionParam() {

        int page = 1;
        int size = 0;
        int rest=1;

        assertThrows(ErrorExceptionParam.class, () ->
                        dishService.getAllDishAtRestaurant(page, size, null,rest),
                ConstantsDomain.PAGE_OR_SIZE_ERROR
        );

        verify(persistanceDish, never()).getAllDishAtRestaurant(anyInt(), anyInt(),anyInt());
        verify(persistanceDish, never()).getAllDishWithFilterCategory(anyInt(), anyInt(), anyString(),anyInt());
    }

    @Test
    void getAllDish_WhenCategoryDoesNotExist_ThrowsExceptionCategoryNotFound() {

        int page = 1;
        int size = 10;
        int rest=1;

        String category = "NonExistentCategory";

        when(categoriaServices.existsByName(category.trim())).thenReturn(false);
        when(restaurantService.findById(rest)).thenReturn(restaurant);

        assertThrows(ExceptionCategoryNotFound.class, () ->
                        dishService.getAllDishAtRestaurant(page, size, category,rest),
                ConstantsDomain.CATEGORY_NOT_FOUND + category
        );

        verify(categoriaServices).existsByName(category.trim());
        verify(persistanceDish, never()).getAllDishWithFilterCategory(anyInt(), anyInt(), anyString(),anyInt());
    }

    @Test
    void getAllDish_WhenCategoryFilterIsEmpty_ReturnsAllDishes() {

        int page = 1;
        int size = 10;
        int rest=1;

        String category = "";
        PaginGeneric<DishResponse> expectedResponse = new PaginGeneric<>();

        when(persistanceDish.getAllDishAtRestaurant(page, size,rest)).thenReturn(expectedResponse);
        when(restaurantService.findById(rest)).thenReturn(restaurant);
        PaginGeneric<DishResponse> result = dishService.getAllDishAtRestaurant(page, size, category,rest);

        assertEquals(expectedResponse, result);
        verify(persistanceDish).getAllDishAtRestaurant(page, size,rest);
        verify(persistanceDish, never()).getAllDishWithFilterCategory(anyInt(), anyInt(), anyString(),anyInt());
    }
}

