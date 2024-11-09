package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.exception.ErrorFeignException;
import com.restaurant.plazoleta.domain.interfaces.IValidateAutorizeFeign;
import com.restaurant.plazoleta.domain.utils.ConstantsDomain;
import com.restaurant.plazoleta.infraestructur.feign.ValidateUserAuthorize;
import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateAutorizeFeignImpl implements IValidateAutorizeFeign {
    private final ValidateUserAuthorize feignClient;

    @Override
    public Boolean validateAdmin() {
        try {
            return feignClient.validateAdmin();
        } catch (FeignException e) {
            if (e.status() == InfraConstants.UNAUTHORIZED_CODE) {
                return false;
            }
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }

    @Override
    public Boolean validateOWNER() {
        try {
            return feignClient.validateOWNER();
        } catch (FeignException e) {
            if (e.status() == InfraConstants.UNAUTHORIZED_CODE) {
                return false;
            }
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }

    @Override
    public Boolean validateCient() {
        try {
            return feignClient.validateCient();
        } catch (FeignException e) {
            if (e.status() == InfraConstants.UNAUTHORIZED_CODE) {
                return false;
            }
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }

    @Override
    public Boolean validateEmployee() {
        try {
            return feignClient.validateEmployee();
        } catch (FeignException e) {
            if (e.status() == InfraConstants.UNAUTHORIZED_CODE) {
                return false;
            }
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }

    @Override
    public Boolean validateToken() {
        try {

            return feignClient.validateToken();
        } catch (FeignException e) {
            if (e.status() == InfraConstants.UNAUTHORIZED_CODE) {
                return false;
            }
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }

    @Override
    public Integer getUserId() {
        try {
       return feignClient.getUserId();
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE)+e);
        }
    }
}
