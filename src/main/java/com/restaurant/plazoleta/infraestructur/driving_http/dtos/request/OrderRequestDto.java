package com.restaurant.plazoleta.infraestructur.driving_http.dtos.request;

import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = InfraConstants.NOT_NULL_FIELDS)
    @Pattern(regexp =InfraConstants.ONLY_BUMBERS_REGEX, message =InfraConstants.ONLY_NUMBER_MESSAGE )
    private Integer  customer;
    @Pattern(regexp =InfraConstants.ONLY_BUMBERS_REGEX, message =InfraConstants.ONLY_NUMBER_MESSAGE )
    @NotNull(message = InfraConstants.NOT_NULL_FIELDS)
    private Integer  chef;
    @Pattern(regexp =InfraConstants.ONLY_BUMBERS_REGEX, message =InfraConstants.ONLY_NUMBER_MESSAGE )
    @NotNull(message = InfraConstants.NOT_NULL_FIELDS)
    private Integer restaurantId;
    @NotNull(message = InfraConstants.NOT_NULL_FIELDS)
    private List<OrderDishDto> orderDishes;
}