package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private int id;
    private String addressName;
    private String city;
    private String district;
    private String ward;
    private boolean isDefaultAddress;
}
