package com.nashtech.minhtran.gearshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "address", indexes = {@Index(name = "address_index", columnList = "id", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column(name = "addressName", nullable = false)
    private String addressName;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "district")
    private String district;

    @NotBlank
    @Column(name = "ward")
    private String ward;

    @Column(name = "isDefaultAddress")
    private boolean isDefaultAddress;


}
