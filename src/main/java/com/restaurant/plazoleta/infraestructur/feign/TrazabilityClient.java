package com.restaurant.plazoleta.infraestructur.feign;


import com.restaurant.plazoleta.domain.model.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "trazabilidadaplication", url = "${trazability.client.url}", configuration = FeignConfig.class)
public interface TrazabilityClient {
    @PostMapping("LogStatus/register")
    void registerTrazabilityStatus(@RequestParam("orderId") Integer orderId,
                                   @RequestParam("customID" ) Integer customID,
                                   @RequestParam("clientEmail") String clientEmail );

    @PatchMapping("LogStatus/registerChange")
    void registerTrazabilityChangeStatus(@RequestParam("status") OrderStatus status,
                                         @RequestParam("orderId" ) Integer orderId,
                                         @RequestParam("employeEmail") String employeEmail,
                                         @RequestParam("employeId") Integer employeId
                                         );
}
