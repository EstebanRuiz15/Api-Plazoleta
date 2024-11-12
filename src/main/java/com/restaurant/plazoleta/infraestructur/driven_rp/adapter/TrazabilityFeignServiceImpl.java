package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.exception.ErrorFeignException;
import com.restaurant.plazoleta.domain.interfaces.ITrazabilityFeignService;
import com.restaurant.plazoleta.domain.model.OrderStatus;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.feign.TrazabilityClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrazabilityFeignServiceImpl implements ITrazabilityFeignService {
    private final TrazabilityClient feignClient;

    @Override
    public void registerTrazabilityStatus(Integer orderId, Integer customID, String clientEmail) {
        try {
            feignClient.registerTrazabilityStatus(orderId, customID, clientEmail);
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE) + e);
        }
    }

    @Override
    public void registerTrazabilityChangeStatus(OrderStatus status, Integer orderId, String employeEmail, Integer employeId) {
        try {
            feignClient.registerTrazabilityChangeStatus(status, orderId, employeEmail, employeId);
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE) + e);

        }
    }
}