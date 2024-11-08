package com.restaurant.plazoleta.aplication;

import com.restaurant.plazoleta.domain.interfaces.ICategoriaPersistance;
import com.restaurant.plazoleta.domain.interfaces.IDishPersistance;
import com.restaurant.plazoleta.domain.services.CategoryServicesImpl;
import com.restaurant.plazoleta.domain.services.DishServiceImpl;
import com.restaurant.plazoleta.domain.services.RestaurantServiceImpl;
import com.restaurant.plazoleta.domain.interfaces.IRestaurantPersistance;
import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
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
    public DishServiceImpl getDishServiceImpl(IDishPersistance persistanceDish, ICategoriaPersistance categoriaServices, IRestaurantPersistance restaurantService) {
        return new DishServiceImpl(persistanceDish,categoriaServices, restaurantService);
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice Plazoleta")
                        .version("1.0.0")
                        .description("This service is for create, update, get and others methods to restaurant, dish, category, and food order"));
    }

}