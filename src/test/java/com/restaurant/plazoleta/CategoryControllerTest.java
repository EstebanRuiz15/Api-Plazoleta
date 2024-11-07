package com.restaurant.plazoleta;

import com.restaurant.plazoleta.domain.interfaces.ICategoriaServices;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.driving_http.controllers.CategoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    @Mock
    private ICategoriaServices service;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testCreateCategory_Success() throws Exception {
        String categoryName = "Italian";

        doNothing().when(service).createCategory(categoryName);

        mockMvc.perform(post("/Category/")
                        .param("name", categoryName))
                .andExpect(status().isCreated())
                .andExpect(content().string(ConstantsDomain.CREAT_SUCCESSFULL));

        verify(service, times(1)).createCategory(categoryName);
    }


}