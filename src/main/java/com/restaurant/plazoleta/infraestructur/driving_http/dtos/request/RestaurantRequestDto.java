package com.restaurant.plazoleta.infraestructur.driving_http.dtos.request;

import com.restaurant.plazoleta.infraestructur.util.InfraConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RestaurantRequestDto {

        @NotNull(message = InfraConstants.RESTAURANT_NAME_EMPTY)
        @Pattern(regexp = InfraConstants.RESTAURANT_NAME_REGEX, message = InfraConstants.RESTAURANT_NAME_INVALID)
        private String name;

        @NotNull(message = InfraConstants.NIT_EMPTY)
        @Digits(integer =InfraConstants.FIVETEEN, fraction = InfraConstants.ZERO, message = InfraConstants.NIT_INVALID)
        private Long nit;

        @NotBlank(message = InfraConstants.ADDRESS_EMPTY)
        private String address;

        @NotBlank(message = InfraConstants.PHONE_NUMBER_EMPTY)
        @Pattern(regexp = InfraConstants.PHONE_NUMBER_REGEX, message = InfraConstants.PHONE_NUMBER_INVALID)
        private String phoneNumber;

        @NotBlank(message = InfraConstants.LOGO_URL_EMPTY)
        @URL(message = InfraConstants.LOGO_URL_INVALID)
        private String logoUrl;

        @Digits(integer = InfraConstants.FIVETEEN, fraction = InfraConstants.ZERO, message = InfraConstants.USER_ID_INVALID)
        @Positive
        private int owner;
}
