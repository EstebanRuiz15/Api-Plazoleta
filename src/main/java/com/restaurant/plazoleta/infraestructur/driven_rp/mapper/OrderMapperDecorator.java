package com.restaurant.plazoleta.infraestructur.driven_rp.mapper;

import com.restaurant.plazoleta.domain.interfaces.IUserServiceClient;
import com.restaurant.plazoleta.domain.model.Order;
import com.restaurant.plazoleta.domain.model.OrderResponse;
import com.restaurant.plazoleta.domain.model.PaginGeneric;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapperDecorator implements IOrderToEntityMApper {

    @Autowired
    private IUserServiceClient userServiceClient;

    @Autowired
    @Qualifier("delegate")
    private IOrderToEntityMApper delegate;

    @Override
    public OrderEntity toEntity(Order order) {
        return delegate.toEntity(order);
    }

    @Override
    public Order toOrder(OrderEntity entity) {
        return delegate.toOrder(entity);
    }

    @Override
    public PaginGeneric<OrderResponse> toOrderPageable(Page<OrderEntity> page) {

        PaginGeneric<OrderResponse> result = new PaginGeneric<>();
        result.setCurrentPage(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalpages(page.getTotalPages());
        long totalElements = page.getTotalElements();
        if (totalElements > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Total elements exceed maximum integer value");
        }
        result.setTotalData((int) totalElements);
        List<OrderResponse> orderResponses = page.getContent().stream()
                .map(this::toOrderResponse)
                .toList();

        result.setItems(orderResponses);
        return result;
    }

    @Override
    public OrderResponse toOrderResponse(OrderEntity orderEntity) {
        OrderResponse response = delegate.toOrderResponse(orderEntity);
        response.setCustomer(getUserName(orderEntity.getCustomer()));
        response.setChef(getUserName(orderEntity.getChef()));
        return response;
    }

    @Override
    public String getCustomerName(Long customerId) {
        return getUserName(customerId);
    }

    @Override
    public String getChefName(Long chefId) {
        return getUserName(chefId);
    }

    @Override
    public String getUserName(Long userId) {
        if (userId == null) return null;
        try {
            return userServiceClient.GetUser(userId.intValue()).getName();
        } catch (Exception e) {
            return "Usuario " + userId;
        }
    }
}
