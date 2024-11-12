package com.restaurant.plazoleta.domain.services;

import com.restaurant.plazoleta.domain.exception.*;
import com.restaurant.plazoleta.domain.interfaces.*;
import com.restaurant.plazoleta.domain.model.*;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderServiceImpl implements IOrderServices {
    private final IOrderPersistance persistance;
    private final IRestaurantService restServie;
    private final IDishService dishService;
    private final IUserServiceClient userFeignClient;
    private final ITrazabilityFeignService logStatuService;

    public OrderServiceImpl(IOrderPersistance persistance, IRestaurantService restServie, IDishService dishService, IUserServiceClient userFeignClient, ITrazabilityFeignService logStatuService) {
        this.persistance = persistance;
        this.restServie = restServie;
        this.dishService = dishService;
        this.userFeignClient = userFeignClient;
        this.logStatuService = logStatuService;
    }

    @Override
    public void registerOrder(Order order) {
        Restaurant restaurant=restServie.findById(order.getRestaurantId());
        User client=userFeignClient.getEmploye();
        User chef=userFeignClient.getChefAtRestaurant(restaurant.getId());
        Order existOrderPending=persistance.findByCustomerAndStatus(client.getId(), OrderStatus.PENDING);
        if(existOrderPending != null)
            throw new ErrorExceptionConflict(ConstantsDomain.NOW_ALREADY_PENDING_ORDER);
        if (restaurant == null)
            throw new ExceptionRestaurantNotFound(ConstantsDomain.RESTAURANT_NOT_FOUND);
        if(client == null)
            throw new ExceptionNotFoundUser(ConstantsDomain.NOT_FOUND_CLIENT);
        if(chef == null)
            throw new ExceptionNotFoundUser(ConstantsDomain.NOT_FOUND_CHEF);

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
        order.setCustomer(client.getId());
        order.setChef(chef.getId());
        persistance.registerOrder(order, restaurant, generateSecurityPin(), client.getEmail());
    }

    @Override
    public PaginGeneric<OrderResponse> getOrdersAtRestaurant(Integer page, Integer size, String status) {
        if(page<1 || size <1)throw  new ErrorExceptionParam(ConstantsDomain.PAGE_OR_SIZE_ERROR);
        if(!status.equalsIgnoreCase(OrderStatus.PENDING.toString()) && !status.equalsIgnoreCase
                        (OrderStatus.READY.toString()) && !status.equalsIgnoreCase(OrderStatus.IN_PREPARATION.toString())
                && !status.equalsIgnoreCase(OrderStatus.DELIVERED.toString())&& !status.equalsIgnoreCase(OrderStatus.CANCELED.toString())) {
        throw new ErrorExceptionParam(ConstantsDomain.INVALID_STATUS_PARAM);
        }

        Integer restaurant=userFeignClient.getEmploye().getRest_id();
        status=status.toUpperCase();
        OrderStatus sta=OrderStatus.PENDING;
        if(status.equals(OrderStatus.IN_PREPARATION.toString()))sta=OrderStatus.IN_PREPARATION;
        if(status.equals(OrderStatus.READY.toString()))sta=OrderStatus.READY;
        if(status.equals(OrderStatus.DELIVERED.toString()))sta=OrderStatus.DELIVERED;
        if(status.equals(OrderStatus.CANCELED.toString()))sta=OrderStatus.CANCELED;



        return persistance.getOrdersAtRestaurantAnStatus(page,size,restaurant, sta);
    }

    @Override
    public void assigned_employee_id(Integer orderID) {
        Order order=persistance.findById(orderID);
        if(order == null) throw new ExceptionOrderNotFound(ConstantsDomain.ORDER_NOT_FOUND);
        Integer assigned=order.getAssigned_employee_id();
        if(assigned != null)throw new ErrorExceptionConflict(ConstantsDomain.ORDER_IS_ALREADY_ASSIGNED);
        if(order.getStatus() != OrderStatus.PENDING )throw new ErrorExceptionConflict(ConstantsDomain.ORDER_IS_ALREADY_ASSIGNED);

        User employe=userFeignClient.getEmploye();
        Integer employeId=employe.getId();
        persistance.assigned_employee_id(employeId, orderID);
        logStatuService.registerTrazabilityChangeStatus(OrderStatus.IN_PREPARATION,orderID, employe.getEmail(), employe.getId());
    }

    @Override
    public void deliveredOrder(String pin) {
        Order order=persistance.findBySecurityPin(pin);
        if(order == null)throw new ExceptionOrderNotFound(ConstantsDomain.ORDER_NOT_FOUND_OR_PIN_ERROR);
        if(order.getStatus() == OrderStatus.DELIVERED)
            throw new ErrorExceptionConflict(ConstantsDomain.ORDER_IS_ALREADY_DELIVERED);
        if(order.getStatus() != OrderStatus.READY)
           throw new ErrorExceptionConflict(ConstantsDomain.ORDER_NOT_REAY);
        persistance.deliveredOrder(order);
        logStatuService.registerTrazabilityChangeStatus(OrderStatus.DELIVERED,order.getId(),null,null);

    }

    @Override
    public void canceledOrder() {
        User user=userFeignClient.getEmploye();
        if(user == null)throw new ExceptionNotFoundUser(ConstantsDomain.NOT_FOUND_CLIENT);
        Order order=persistance.findByCustomerAndStatus(user.getId(), OrderStatus.PENDING);
        if(order == null)throw new ErrorExceptionConflict(ConstantsDomain.ORDER_NOT_CANCELLED);
        persistance.canceledOrder(order.getId());
        logStatuService.registerTrazabilityChangeStatus(OrderStatus.CANCELED,order.getId(),null,null);

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
    private String generateSecurityPin() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
