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

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/restaurant/") || requestURI.startsWith("/Category/") ) {
            if (!authServiceClient.validateAdmin()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Unauthorized for this method");
                return false;
            }
        }

        if (requestURI.startsWith("/Dish/update/{id}") || requestURI.startsWith("/Dish/")
                || requestURI.startsWith("/Dish/enable/{id}")||requestURI.startsWith("/Dish/disable/{id}") ) {
            if (!authServiceClient.validateOWNER()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Unauthorized for this method");
                return false;
            }
        }

        return true;
    }
}