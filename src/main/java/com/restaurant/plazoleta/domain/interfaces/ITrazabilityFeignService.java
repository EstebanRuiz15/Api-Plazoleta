package com.restaurant.plazoleta.domain.interfaces;

import com.restaurant.plazoleta.domain.model.OrderStatus;
public interface ITrazabilityFeignService {
    void registerTrazabilityStatus( Integer orderId, Integer customID, String clientEmail );

    void registerTrazabilityChangeStatus(OrderStatus status,Integer orderId,String employeEmail, Integer employeId
    );
}
