package com.restaurant.plazoleta;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ExceptionCategory;
import com.restaurant.plazoleta.domain.interfaces.ICategoriaPersistance;
import com.restaurant.plazoleta.domain.model.Category;
import com.restaurant.plazoleta.domain.services.CategoryServicesImpl;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServicesImplTest {

    @Mock
    private ICategoriaPersistance persistance;

    @InjectMocks
    private CategoryServicesImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory_EmptyName_ShouldThrowErrorExceptionParam() {

        String categoryName = " ";

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            categoryService.createCategory(categoryName);
        });

        assertEquals(ConstantsDomain.NAME_EMPTY, exception.getMessage());
    }

    @Test
    public void testCreateCategory_CategoryAlreadyExists_ShouldThrowExceptionCategory() {

        String categoryName = "Test Category";
        Category existingCategory = new Category(categoryName);

        when(persistance.findByName(categoryName)).thenReturn(existingCategory);

        ExceptionCategory exception = assertThrows(ExceptionCategory.class, () -> {
            categoryService.createCategory(categoryName);
        });

        assertEquals(ConstantsDomain.NAME_AL_READY_EXIST, exception.getMessage());
        verify(persistance, never()).save(anyString());
    }

    @Test
    public void testCreateCategory_ValidCategory_ShouldCallSave() {

        String categoryName = "New Category";
        when(persistance.findByName(categoryName)).thenReturn(null);
        categoryService.createCategory(categoryName);

        verify(persistance, times(1)).save(categoryName);
    }
}