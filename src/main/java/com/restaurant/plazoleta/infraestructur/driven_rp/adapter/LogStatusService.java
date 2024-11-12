package com.restaurant.plazoleta.infraestructur.driven_rp.adapter;

import com.restaurant.plazoleta.domain.exception.ErrorFeignException;
import com.restaurant.plazoleta.domain.interfaces.ILogStatusService;
import com.restaurant.plazoleta.domain.model.Order;
import com.restaurant.plazoleta.domain.model.OrderStatus;
import com.restaurant.plazoleta.infraestructur.driven_rp.entity.OrderStatusLog;
import com.restaurant.plazoleta.infraestructur.driven_rp.persistence.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class LogStatusService implements ILogStatusService {
    private final OrderStatusRepository repositoryMongo;
    @Override
    public void registerStar(Order order, String clientEmail) {
        OrderStatusLog logStatus= new OrderStatusLog();
        logStatus.setDateStar(dateFormate());
        logStatus.setCustomerId(order.getCustomer().intValue());
        logStatus.setCustomerEmail(clientEmail);
        logStatus.setOrderId(order.getId());
        logStatus.setNewStatus(OrderStatus.PENDING.toString());
        repositoryMongo.save(logStatus);
    }


    @Override
    public void registerChange(OrderStatus status, Integer ordeId, String employeEmail, Integer employeId) {

            OrderStatusLog statusLog = repositoryMongo.findByOrderId(ordeId).get();
            String lastStatus = statusLog.getNewStatus();
            if (statusLog.getCustomerEmail().isEmpty()) {
                statusLog.setEmployeeId(employeId);
                statusLog.setEmployeeEmail(employeEmail);
            }
            statusLog.setNewStatus(status.toString());
            statusLog.setPreviousStatus(lastStatus);
            statusLog.setLastUpdate(dateFormate());
            repositoryMongo.save(statusLog);

    }

    private String dateFormate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return  now.format(formatter);
    }
}
