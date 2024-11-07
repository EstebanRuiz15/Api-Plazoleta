package com.restaurant.plazoleta;

import com.restaurant.plazoleta.domain.exception.ExceptionDishNotFound;
import com.restaurant.plazoleta.domain.model.Dish;
import com.restaurant.plazoleta.infraestructur.driving_http.controllers.DishController;
import com.restaurant.plazoleta.domain.interfaces.IDishService;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishModifyDto;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.DishRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IDishRequestMapper;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 class DishControllerTest {

    @Mock
    private IDishService service;

    @Mock
    private IDishRequestMapper mapperRequest;

    @InjectMocks
    private DishController dishController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();
    }

    @Test
    void testCreateNewDish_Success() throws Exception {
        // Arrange
        DishRequestDto requestDto = new DishRequestDto();
        requestDto.setName("Dish Name");
        requestDto.setCategory(1);
        requestDto.setDescription("Delicious dish");
        requestDto.setPrice(15.99);
        requestDto.setRestaurant(1);
        requestDto.setImageUrl("http://example.com/dish-image.jpg");

        Dish dishEntity = new Dish();  // Usa la clase real de Dish
        when(mapperRequest.toDish(requestDto)).thenReturn(dishEntity);

        doNothing().when(service).createDish(any());

        mockMvc.perform(post("/Dish/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Dish Name\", \"category\": 1, \"description\": \"Delicious dish\", \"price\": 15.99, \"restaurant\": 1, \"imageUrl\": \"http://example.com/dish-image.jpg\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string(InfraConstants.DISH_SUCCES));

        verify(service, times(1)).createDish(any());
    }

    @Test
    void modifyDish_ShouldReturnOk_WhenDishIsModifiedSuccessfully() throws Exception {
        DishModifyDto modifyDto = new DishModifyDto();
        modifyDto.setName("Updated Dish");
        modifyDto.setDescription("Updated Description");
        modifyDto.setPrice(150.0);

        doNothing().when(service).modifyDish(any(), anyInt());

        mockMvc.perform(patch("/Dish/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Dish\", \"description\": \"Updated Description\", \"price\": 150.0 }"))
                .andExpect(status().isOk());

        verify(service, times(1)).modifyDish(any(), eq(1));
    }

}