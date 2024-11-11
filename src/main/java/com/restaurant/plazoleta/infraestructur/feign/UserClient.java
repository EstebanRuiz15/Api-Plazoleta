package com.restaurant.plazoleta.infraestructur.feign;

import com.restaurant.plazoleta.domain.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

    @Component
    @FeignClient(name = "useraplication", url = "${user.client.url}", configuration = FeignConfig.class)
    public interface UserClient {
        @GetMapping("users/getUser")
        User getUserById(@RequestParam("userId") Integer userId);
        @GetMapping("users/getEmploye")
        User getEmploye();


    }

