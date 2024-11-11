package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.ErrorExceptionParam;
import com.restaurant.plazoleta.domain.exception.ExceptionDishNotFound;
import com.restaurant.plazoleta.domain.exception.ExceptionNotFoundUser;
import com.restaurant.plazoleta.domain.exception.ExceptionRestaurantNotFound;
import com.restaurant.plazoleta.domain.interfaces.*;
import com.restaurant.plazoleta.domain.model.*;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.driven_rp.adapter.OrderPersistanceImpli;
import org.apache.tomcat.util.bcel.Const;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderServiceImpl implements IOrderServices {
    private final IOrderPersistance persistance;
    private final IRestaurantService restServie;
    private final IDishService dishService;
    private final IUserServiceClient userFeignClient;

    public OrderServiceImpl(IOrderPersistance persistance, IRestaurantService restServie, IDishService dishService, IUserServiceClient userFeignClient) {
        this.persistance = persistance;
        this.restServie = restServie;
        this.dishService = dishService;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public void registerOrder(Order order) {
        Restaurant restaurant=restServie.findById(order.getRestaurantId());
        User client=userFeignClient.GetUser(order.getCustomer());
        User chef=userFeignClient.GetUser(order.getChef());
        if (restaurant== null)throw new ExceptionRestaurantNotFound(ConstantsDomain.RESTAURANT_NOT_FOUND);
        if(client == null) throw new ExceptionNotFoundUser(ConstantsDomain.NOT_FOUND_CLIENT);
        if(chef == null) throw new ExceptionNotFoundUser(ConstantsDomain.NOT_FOUND_CHEF);

        List<OrderDish> listDish=order.getOrderDishes();
        List<Dish> listDishAtRestaurant=dishService.getAllDishAtRestaurantActive(restaurant.getId());
        Boolean isValid=listDishIsValid(listDish,listDishAtRestaurant);

        for (OrderDish orderDish : listDish) {
            for (Dish dish : listDishAtRestaurant) {
                if (orderDish.getDishId().equals(dish.getId())) {
                    orderDish.setDish(dish);
                    orderDish.setOrder(order);
                    break;
                }
            }
        }
        order.setOrderDishes(listDish);
        persistance.registerOrder(order, restaurant);

    }

    @Override
    public PaginGeneric<OrderResponse> getOrdersAtRestaurant(Integer page, Integer size, String status) {
        if(page<1 || size <1)throw  new ErrorExceptionParam(ConstantsDomain.PAGE_OR_SIZE_ERROR);
        if(!status.equalsIgnoreCase(OrderStatus.PENDING.toString()) && !status.equalsIgnoreCase
                        (OrderStatus.READY.toString()) && !status.equalsIgnoreCase(OrderStatus.IN_PREPARATION.toString())) {
        throw new ErrorExceptionParam(ConstantsDomain.INVALID_STATUS_PARAM);
        }

        Integer restaurant=userFeignClient.getEmploye().getRest_id();
        status=status.toUpperCase();
        OrderStatus sta=OrderStatus.PENDING;
        if(status.equals(OrderStatus.IN_PREPARATION.toString()))sta=OrderStatus.IN_PREPARATION;
        if(status.equals(OrderStatus.READY.toString()))sta=OrderStatus.READY;


        return persistance.getOrdersAtRestaurantAnStatus(page,size,restaurant, sta);
    }

    private Boolean listDishIsValid(List<OrderDish> orderDishes,List<Dish> dishes ){

        Set<Integer> dishIdsAtRestaurant = dishes.stream()
                .map(Dish::getId)
                .collect(Collectors.toSet());

        Set<Integer> orderDishIds = orderDishes.stream()
                .map(OrderDish::getDishId)
                .collect(Collectors.toSet());

        boolean allDishesPresent = orderDishIds.stream()
                .allMatch(dishIdsAtRestaurant::contains);

        if (!allDishesPresent) {
            throw new ExceptionDishNotFound(ConstantsDomain.SOME_DISHES_NOT_FOUND);
        }
        return true;
    }
}
