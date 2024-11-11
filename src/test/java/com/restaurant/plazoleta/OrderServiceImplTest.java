package com.restaurant.plazoleta;

import com.restaurant.plazoleta.domain.exception.*;
import com.restaurant.plazoleta.domain.interfaces.IDishService;
import com.restaurant.plazoleta.domain.interfaces.IOrderPersistance;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantService;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.*;
import com.restaurant.plazoleta.domain.services.OrderServiceImpl;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private IOrderPersistance orderPersistance;
    private IRestaurantService restaurantService;
    private IDishService dishService;
    private IUserServiceClient userServiceClient;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderPersistance = mock(IOrderPersistance.class);
        restaurantService = mock(IRestaurantService.class);
        dishService = mock(IDishService.class);
        userServiceClient = mock(IUserServiceClient.class);
        orderService = new OrderServiceImpl(orderPersistance, restaurantService, dishService, userServiceClient);
    }

    @Test
    void registerOrder_successful() {
        Order order = createOrder();
        Restaurant restaurant = createRestaurant();
        User customer = createUser("Customer");
        User chef = createUser("Chef");
        List<Dish> dishes = createDishes();
        String pin="12322";

        when(restaurantService.findById(order.getRestaurantId())).thenReturn(restaurant);
        when(userServiceClient.GetUser(order.getCustomer())).thenReturn(customer);
        when(userServiceClient.GetUser(order.getChef())).thenReturn(chef);
        when(dishService.getAllDishAtRestaurantActive(order.getRestaurantId())).thenReturn(dishes);

        assertDoesNotThrow(() -> orderService.registerOrder(order));

        verify(orderPersistance, times(1)).registerOrder(order, restaurant, pin);
    }

    @Test
    void registerOrder_restaurantNotFound() {
        Order order = createOrder();
        when(restaurantService.findById(order.getRestaurantId())).thenReturn(null);

        Exception exception = assertThrows(ExceptionRestaurantNotFound.class, () -> orderService.registerOrder(order));
        assertEquals(ConstantsDomain.RESTAURANT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void registerOrder_customerNotFound() {
        Order order = createOrder();
        Restaurant restaurant = createRestaurant();

        when(restaurantService.findById(order.getRestaurantId())).thenReturn(restaurant);
        when(userServiceClient.GetUser(order.getCustomer())).thenReturn(null);

        Exception exception = assertThrows(ExceptionNotFoundUser.class, () -> orderService.registerOrder(order));
        assertEquals(ConstantsDomain.NOT_FOUND_CLIENT, exception.getMessage());
    }

    @Test
    void registerOrder_chefNotFound() {
        Order order = createOrder();
        Restaurant restaurant = createRestaurant();
        User customer = createUser("Customer");

        when(restaurantService.findById(order.getRestaurantId())).thenReturn(restaurant);
        when(userServiceClient.GetUser(order.getCustomer())).thenReturn(customer);
        when(userServiceClient.GetUser(order.getChef())).thenReturn(null);

        Exception exception = assertThrows(ExceptionNotFoundUser.class, () -> orderService.registerOrder(order));
        assertEquals(ConstantsDomain.NOT_FOUND_CHEF, exception.getMessage());
    }

    @Test
    void registerOrder_dishNotFound() {
        Order order = createOrder();
        Restaurant restaurant = createRestaurant();
        User customer = createUser("Customer");
        User chef = createUser("Chef");
        List<Dish> dishes = Arrays.asList(createDish(1), createDish(2)); // Only two dishes available

        when(restaurantService.findById(order.getRestaurantId())).thenReturn(restaurant);
        when(userServiceClient.GetUser(order.getCustomer())).thenReturn(customer);
        when(userServiceClient.GetUser(order.getChef())).thenReturn(chef);
        when(dishService.getAllDishAtRestaurantActive(order.getRestaurantId())).thenReturn(dishes);

        Exception exception = assertThrows(ExceptionDishNotFound.class, () -> orderService.registerOrder(order));
        assertEquals(ConstantsDomain.SOME_DISHES_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getOrdersAtRestaurant_WhenValidParams_ReturnsPagedOrders() {

        Integer page = 1;
        Integer size = 10;
        String status = "PENDING";
        Integer restaurantId = 1;

        User mockUser = new User();
        mockUser.setRest_id(restaurantId);
        when(userServiceClient.getEmploye()).thenReturn(mockUser);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1);
        orderResponse.setCustomer("Customer 1");
        orderResponse.setChef("Chef 1");
        orderResponse.setStatus(OrderStatus.PENDING);
        orderResponse.setDate(LocalDateTime.now());

        PaginGeneric<OrderResponse> pagedResponse = new PaginGeneric<>();
        pagedResponse.setItems(Arrays.asList(orderResponse));
        pagedResponse.setCurrentPage(page);
        pagedResponse.setSize(size);
        pagedResponse.setTotalpages(1);
        pagedResponse.setTotalData(1);

        when(orderPersistance.getOrdersAtRestaurantAnStatus(page, size, restaurantId, OrderStatus.PENDING))
                .thenReturn(pagedResponse);

        PaginGeneric<OrderResponse> result = orderService.getOrdersAtRestaurant(page, size, status);

        assertEquals(pagedResponse, result);
        verify(orderPersistance).getOrdersAtRestaurantAnStatus(page, size, restaurantId, OrderStatus.PENDING);
    }
    @Test
    void getOrdersAtRestaurant_WhenInvalidStatus_ThrowsException() {
        // Arrange
        Integer page = 1;
        Integer size = 10;
        String status = "INVALID_STATUS";

        // Act & Assert
        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            orderService.getOrdersAtRestaurant(page, size, status);
        });

        assertEquals(ConstantsDomain.INVALID_STATUS_PARAM, exception.getMessage());
    }

    @Test
    void getOrdersAtRestaurant_WhenPageOrSizeLessThan1_ThrowsException() {

        Integer page = 0;
        Integer size = 10;
        String status = "PENDING";

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            orderService.getOrdersAtRestaurant(page, size, status);
        });

        assertEquals(ConstantsDomain.PAGE_OR_SIZE_ERROR, exception.getMessage());
    }

    @Test
    void assigned_employee_id_Success() {

        Integer orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        order.setAssigned_employee_id(null);

        User employee = new User();
        employee.setId(100);

        when(orderPersistance.findById(orderId)).thenReturn(order);
        when(userServiceClient.getEmploye()).thenReturn(employee);

        orderService.assigned_employee_id(orderId);

        verify(orderPersistance).assigned_employee_id(employee.getId(), orderId);
    }

    @Test
    void assigned_employee_id_OrderNotFound() {

        Integer orderId = 1;
        when(orderPersistance.findById(orderId)).thenReturn(null);

        ExceptionOrderNotFound exception = assertThrows(ExceptionOrderNotFound.class, () -> {
            orderService.assigned_employee_id(orderId);
        });

        assertEquals(ConstantsDomain.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void assigned_employee_id_OrderAlreadyAssigned() {

        Integer orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        order.setAssigned_employee_id(100);

        when(orderPersistance.findById(orderId)).thenReturn(order);

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            orderService.assigned_employee_id(orderId);
        });

        assertEquals(ConstantsDomain.ORDER_IS_ALREADY_ASSIGNED, exception.getMessage());
    }
    @Test
    void testDeliveredOrder_OrderNotFound() {

        String pin = "1234";
        when(orderPersistance.findBySecurityPin(pin)).thenReturn(null);

        ExceptionOrderNotFound exception = assertThrows(ExceptionOrderNotFound.class, () -> {
            orderService.deliveredOrder(pin);
        });

        assertEquals("Order not found or wrong pin", exception.getMessage());
    }

    @Test
    void testDeliveredOrder_OrderAlreadyDelivered() {
        String pin = "1234";
        Order order = new Order();
        order.setStatus(OrderStatus.DELIVERED);
        when(orderPersistance.findBySecurityPin(pin)).thenReturn(order);

        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            orderService.deliveredOrder(pin);
        });

        assertEquals("Order is already delivered", exception.getMessage());
    }

    @Test
    void testDeliveredOrder_OrderNotReady() {
        String pin = "1234";
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        when(orderPersistance.findBySecurityPin(pin)).thenReturn(order);
        ErrorExceptionParam exception = assertThrows(ErrorExceptionParam.class, () -> {
            orderService.deliveredOrder(pin);
        });

        assertEquals("the order is not ready yet", exception.getMessage());
    }

    @Test
    void testDeliveredOrder_SuccessfulDelivery() {
        String pin = "1234";
        Order order = new Order();
        order.setStatus(OrderStatus.READY);
        when(orderPersistance.findBySecurityPin(pin)).thenReturn(order);

        orderService.deliveredOrder(pin);

        verify(orderPersistance, times(1)).deliveredOrder(order);
    }

    @Test
    void testCanceledOrderSuccessfully() {
        User user = new User();
        user.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setStatus(OrderStatus.PENDING);

        when(userServiceClient.getEmploye()).thenReturn(user);
        when(orderPersistance.findByCustomerAndStatus(user.getId(), OrderStatus.PENDING)).thenReturn(order);
        orderService.canceledOrder();

        verify(orderPersistance, times(1)).canceledOrder(order.getId());
    }

    @Test
    void testCanceledOrder_UserNotFound() {
        when(userServiceClient.getEmploye()).thenReturn(null);
        ExceptionNotFoundUser exception = assertThrows(ExceptionNotFoundUser.class, () -> orderService.canceledOrder());
        assertEquals(ConstantsDomain.NOT_FOUND_CLIENT, exception.getMessage());
    }

    @Test
    void testCanceledOrder_OrderNotFound() {
        User user = new User();
        user.setId(1);

        when(userServiceClient.getEmploye()).thenReturn(user);
        when(orderPersistance.findByCustomerAndStatus(user.getId(), OrderStatus.PENDING)).thenReturn(null);

        ErrorExceptionConflict exception = assertThrows(ErrorExceptionConflict.class, () -> orderService.canceledOrder());
        assertEquals(ConstantsDomain.ORDER_NOT_CANCELLED, exception.getMessage());
    }



    private Order createOrder() {
        Order order = new Order();
        order.setCustomer(1);
        order.setChef(2);
        order.setStatus(OrderStatus.PENDING);
        order.setDate(LocalDateTime.now());
        order.setOrderDishes(Arrays.asList(createOrderDish(1), createOrderDish(2), createOrderDish(3)));
        order.setRestaurantId(1);
        return order;
    }

    private Restaurant createRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("Test Restaurant");
        restaurant.setNit(123456789L);
        restaurant.setAddress("Test Address");
        restaurant.setPhoneNumber("1234567890");
        restaurant.setLogoUrl("http://test.com/logo.png");
        restaurant.setOwner(1);
        return restaurant;
    }

    private User createUser(String role) {
        User user = new User();
        user.setName("Test");
        user.setLastName("User");
        user.setDocument(12345678);
        user.setCelPhone("1234567890");
        user.setBirthDay(new java.util.Date());
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        user.setRol(role);
        return user;
    }

    private Dish createDish(Integer id) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName("Test Dish " + id);
        dish.setPrice(10.0);
        dish.setDescription("Test Description " + id);
        dish.setImageUrl("http://test.com/dish" + id + ".png");
        dish.setRestaurant(1);
        dish.setCategory(1);
        return dish;
    }

    private OrderDish createOrderDish(Integer dishId) {
        OrderDish orderDish = new OrderDish();
        orderDish.setDishId(dishId);
        orderDish.setQuantity(2);
        return orderDish;
    }

    private List<Dish> createDishes() {
        return Arrays.asList(createDish(1), createDish(2), createDish(3));
    }
}