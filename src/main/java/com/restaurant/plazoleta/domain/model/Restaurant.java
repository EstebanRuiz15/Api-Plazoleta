package com.restaurant.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
 private String name;
 private Long nit;
 private String address;
 private String phoneNumber;
 private String logoUrl;
 private int owner;
}
