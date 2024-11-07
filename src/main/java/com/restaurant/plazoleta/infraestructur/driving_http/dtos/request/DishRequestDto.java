package com.restaurant.plazoleta.infraestructur.driving_http.dtos.request;

import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import jakarta.validation.Configuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishRequestDto {

    @NotBlank(message = InfraConstants.NAME_NOT_EMPTY)
    private String name;

    @NotNull(message = InfraConstants.PRICE_POSITIVE_INTEGER)
    @Min(value = 1, message = InfraConstants.PRICE_POSITIVE_INTEGER)
    private  Double price;

    @NotBlank(message = InfraConstants.DESCRIPTION_NOT_EMPTY)
    private String description;

    @NotBlank(message = InfraConstants.IMAGE_URL_VALID)
    @URL(message = InfraConstants.IMAGE_URL_VALID)
    private String imageUrl;

    @NotNull(message = InfraConstants.RESTAURANT_POSITIVE_INTEGER)
    @Min(value = 1, message = InfraConstants.RESTAURANT_POSITIVE_INTEGER)
    private Integer restaurant;

    @NotNull(message = InfraConstants.CATEGORY_POSITIVE_INTEGER)
    @Min(value = 1, message = InfraConstants.CATEGORY_POSITIVE_INTEGER)
    private Integer category;

}
