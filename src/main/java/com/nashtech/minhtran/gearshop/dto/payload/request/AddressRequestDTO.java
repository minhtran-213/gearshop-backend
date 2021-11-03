package com.nashtech.minhtran.gearshop.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {

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
