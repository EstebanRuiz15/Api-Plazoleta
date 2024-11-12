package com.restaurant.plazoleta.infraestructur.driven_rp.persistence;

import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderStatusLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderStatusRepository extends MongoRepository<OrderStatusLog,String> {
    Optional<OrderStatusLog> findByCustomerId(Integer customerId);
    Optional<OrderStatusLog> findByOrderId(Integer orderId);

}
