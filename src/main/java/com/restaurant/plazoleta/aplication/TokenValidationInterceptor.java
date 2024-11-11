package com.restaurant.plazoleta.aplication;

import com.restaurant.plazoleta.domain.interfaces.IValidateAutorizeFeign;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    @Autowired
    @Lazy
    private IValidateAutorizeFeign authServiceClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        String requestURI = request.getRequestURI();

        if(requestURI.startsWith("/swagger-ui") ||requestURI.startsWith( "/v3/api-docs")
                ||requestURI.startsWith( "/restaurant/getRestId")) return true;

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }


        if (requestURI.equals("/restaurant/") || requestURI.equals("/Category/") ) {
            if (authServiceClient.validateAdmin()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Unauthorized for this method");
                return true;
            }
        }

        if (requestURI.startsWith("/Dish/update/") || requestURI.equals("/Dish/")
                || requestURI.startsWith("/Dish/enable")||requestURI.startsWith("/Dish/disable") ) {
            if (authServiceClient.validateOWNER()) {
                return true;
            }
        }

        if(requestURI.equals("/restaurant/getRestaurants") || requestURI.equals("/Dish/getAllDish")
                || requestURI.equals("/order/") || requestURI.equals("/order/cancelled")){
            if (authServiceClient.validateToken()) {
                return true;
            }
        }

        if(requestURI.equals("/order/getOrders") || requestURI.equals("/order/assignedEmploye") || requestURI.equals("/order/delivered")){
            if (authServiceClient.validateEmployee()) {
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Unauthorized for this method");
        return false;
    }
}