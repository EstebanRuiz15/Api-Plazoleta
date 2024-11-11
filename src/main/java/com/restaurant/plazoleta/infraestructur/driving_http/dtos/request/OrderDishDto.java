package com.restaurant.plazoleta.infraestructur.driving_http.dtos.request;

import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishDto {
    @NotNull(message = InfraConstants.NOT_NULL_FIELDS)
    private Long dishId;
    @NotNull(message = InfraConstants.NOT_NULL_FIELDS)
    @Min(value = 1, message = InfraConstants.THE_FIELD_WOLD_POSITIVE)
    private int quantity;
}