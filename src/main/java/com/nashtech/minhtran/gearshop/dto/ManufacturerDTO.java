package com.nashtech.minhtran.gearshop.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class ManufacturerDTO {
    @NotBlank
    private String name;
}
