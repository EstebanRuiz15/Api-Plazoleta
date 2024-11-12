package com.restaurant.plazoleta.aplication;

import com.restaurant.plazoleta.domain.interfaces.*;
import com.restaurant.plazoleta.domain.services.CategoryServicesImpl;
import com.restaurant.plazoleta.domain.services.DishServiceImpl;
import com.restaurant.plazoleta.domain.services.OrderServiceImpl;
import com.restaurant.plazoleta.domain.services.RestaurantServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeansConfig {

    @Bean
    public RestaurantServiceImpl getRestaurantServiceImpl(
            IUserServiceClient userClient, IRestaurantPersistance persistance) {
        return new RestaurantServiceImpl(persistance, userClient);
    }

    @Bean
    public CategoryServicesImpl getCategoryServiceImpl(ICategoriaPersistance persistance) {
        return new CategoryServicesImpl(persistance);
    }
    @Bean
    public DishServiceImpl getDishServiceImpl(IDishPersistance persistanceDish, ICategoriaPersistance categoriaServices, IRestaurantPersistance restaurantService, IValidateAutorizeFeign feignClient) {
        return new DishServiceImpl(persistanceDish,categoriaServices, restaurantService, feignClient);
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice Plazoleta")
                        .version("1.0.0")
                        .description("This service is for create, update, get and others methods to restaurant, dish, category, and food order"));
    }


    @Bean
    public OrderServiceImpl getOrderServiceImpl(IOrderPersistance persistance, IRestaurantService restServie,
                                                IDishService dishService, IUserServiceClient userFeignClient,ILogStatusService logStatuService) {
        return new OrderServiceImpl(persistance, restServie, dishService, userFeignClient, logStatuService);
    }
    }