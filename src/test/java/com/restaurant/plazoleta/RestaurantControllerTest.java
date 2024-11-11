package com.restaurant.plazoleta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.plazoleta.aplication.TokenValidationInterceptor;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantService;
import com.restaurant.plazoleta.domain.interfaces.IValidateAutorizeFeign;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.infraestructur.driving_http.controllers.RestaurantController;
import com.restaurant.plazoleta.infraestructur.driving_http.dtos.request.RestaurantRequestDto;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.IRestauratRequestMapper;
import com.restaurant.plazoleta.infraestructur.driving_http.mappers.RestaurantToDtoMapper;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IRestaurantService restaurantService;

    @MockBean
    private IRestauratRequestMapper restaurantMapper;
    @MockBean
    private IValidateAutorizeFeign authServiceClient;
    @MockBean
    private TokenValidationInterceptor tokenValidationInterceptor;
    @MockBean
    private RestaurantToDtoMapper responseMapper;

    private RestaurantRequestDto validRequest;
    private Restaurant mappedRestaurant;

    @BeforeEach
    void setUp() {
        validRequest = RestaurantRequestDto.builder()
                .name("Valid Restaurant")
                .nit(123456789L)
                .address("Valid Address 123")
                .phoneNumber("+1234567890")
                .logoUrl("http://valid-url.com/logo.png")
                .owner(1)
                .build();

        mappedRestaurant = new Restaurant();

        when(restaurantMapper.toRestaurant(any(RestaurantRequestDto.class)))
                .thenReturn(mappedRestaurant);
    }

    @Test
    void createRestaurant_WithValidRequest_ShouldReturnCreated() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isCreated())
                .andExpect(content().string(InfraConstants.CREATE_RESTAURANT_SUCCES));

        verify(restaurantService).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void createRestaurant_WithEmptyName_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setName(null);
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.name").value(InfraConstants.RESTAURANT_NAME_EMPTY));
    }

    @Test
    void createRestaurant_WithInvalidName_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setName("1111123");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.name").value(InfraConstants.RESTAURANT_NAME_INVALID));
    }

    @Test
    void createRestaurant_WithInvalidNit_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setNit(null);
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.nit").value(InfraConstants.NIT_EMPTY));
    }

    @Test
    void createRestaurant_WithInvalidPhoneNumber_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setPhoneNumber("invalid-phone");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.phoneNumber").value(InfraConstants.PHONE_NUMBER_INVALID));
    }

    @Test
    void createRestaurant_WithInvalidLogoUrl_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setLogoUrl("invalid-url");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.logoUrl").value(InfraConstants.LOGO_URL_INVALID));
    }

    @Test
    void createRestaurant_WithNegativeOwnerId_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setOwner(-1);
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void createRestaurant_WithEmptyAddress_ShouldReturnBadRequest() throws Exception {
        doReturn(true).when(tokenValidationInterceptor).preHandle(any(), any(), any());
        validRequest.setAddress("");
        String requestJson = objectMapper.writeValueAsString(validRequest);

        ResultActions result = mockMvc.perform(post("/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.address").value(InfraConstants.ADDRESS_EMPTY));
    }
}