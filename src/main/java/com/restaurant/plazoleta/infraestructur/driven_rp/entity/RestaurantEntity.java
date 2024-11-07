package com.restaurant.plazoleta.infraestructur.driven_rp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nit", nullable = false)
    private Long nit;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(name="id_owner", nullable = false)
    private int owner;

    public RestaurantEntity() {
    }
}
