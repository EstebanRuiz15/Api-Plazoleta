package com.restaurant.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Restaurant {
 private Integer id;
 private String name;
 private Long nit;
 private String address;
 private String phoneNumber;
 private String logoUrl;
 private int owner;

 public Restaurant(String name, Long nit, String address, String phoneNumber, String logoUrl, int owner) {
  this.name = name;
  this.nit = nit;
  this.address = address;
  this.phoneNumber = phoneNumber;
  this.logoUrl = logoUrl;
  this.owner = owner;
 }
}
