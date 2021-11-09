package com.nashtech.minhtran.gearshop.dto.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RateRequestDTO {

    @NotBlank
    @Min(value = 0)
    private double point;

    private String comment;
}
