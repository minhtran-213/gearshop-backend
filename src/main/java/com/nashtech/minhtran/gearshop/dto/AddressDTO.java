package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    @NotBlank
    private String addressName;
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String ward;
    private boolean isDefaultAddress;
}
