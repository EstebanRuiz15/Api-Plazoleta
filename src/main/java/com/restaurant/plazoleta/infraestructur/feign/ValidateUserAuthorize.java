package com.restaurant.plazoleta.infraestructur.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "useraplication", url = "${user.client.url}", configuration = FeignConfig.class)
public interface ValidateUserAuthorize {
    @GetMapping("auth/validate/admin")
    Boolean validateAdmin();

    @GetMapping("auth/validate/owner")
    Boolean validateOWNER();

    @GetMapping("auth/validate/client")
    Boolean validateCient();

    @GetMapping("auth/validate/employe")
    Boolean validateEmployee();

    @GetMapping("auth/validateToken")
    Boolean validateToken();

    @GetMapping("auth/userId")
    Integer getUserId();

}
