package com.restaurant.plazoleta;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionUserInvalid;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.Restaurant;
import com.restaurant.plazoleta.domain.model.User;
import com.restaurant.plazoleta.domain.services.RestaurantServiceImpl;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private IRestaurantPersistance restaurantPersistance;

    @Mock
    private IUserServiceClient userClient;

    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantServiceImpl(restaurantPersistance, userClient);
    }

    @Test
    void saveRestaurant_WithValidOwner_ShouldSaveSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        User owner = createValidOwner();

        when(userClient.GetUser(restaurant.getOwner())).thenReturn(owner);

        // Act
        restaurantService.saveRestaurant(restaurant);

        // Assert
        verify(restaurantPersistance).saveRestaurant(restaurant, owner);
        verify(userClient).GetUser(restaurant.getOwner());
    }

    @Test
    void saveRestaurant_WithNonExistentUser_ShouldThrowException() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        when(userClient.GetUser(restaurant.getOwner())).thenReturn(null);

        // Act & Assert
        ErrorExceptionUserInvalid exception = assertThrows(
                ErrorExceptionUserInvalid.class,
                () -> restaurantService.saveRestaurant(restaurant)
        );

        assertEquals(
                ConstantsDomain.ERROR_USER + restaurant.getOwner() + ConstantsDomain.NOT_EXIST,
                exception.getMessage()
        );
        verify(restaurantPersistance, never()).saveRestaurant(any(), any());
    }

    @Test
    void saveRestaurant_WithNonOwnerUser_ShouldThrowException() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        User nonOwner = createNonOwnerUser();

        when(userClient.GetUser(restaurant.getOwner())).thenReturn(nonOwner);

        // Act & Assert
        ErrorExceptionUserInvalid exception = assertThrows(
                ErrorExceptionUserInvalid.class,
                () -> restaurantService.saveRestaurant(restaurant)
        );

        assertEquals(
                ConstantsDomain.ERROR_USER + nonOwner.getName() + ConstantsDomain.NOT_HAVE_OWNER_ROL,
                exception.getMessage()
        );
        verify(restaurantPersistance, never()).saveRestaurant(any(), any());
    }

    private Restaurant createValidRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setNit(123456789L);
        restaurant.setAddress("Test Address");
        restaurant.setPhoneNumber("1234567890");
        restaurant.setLogoUrl("http://test.com/logo.png");
        restaurant.setOwner(1);
        return restaurant;
    }

    private User createValidOwner() {
        User owner = new User();
        owner.setName("John Owner");
        owner.setRol(ConstantsDomain.OWNER);
        return owner;
    }

    private User createNonOwnerUser() {
        User user = new User();
        user.setName("John Employee");
        user.setRol("EMPLOYEE");
        return user;
    }
}