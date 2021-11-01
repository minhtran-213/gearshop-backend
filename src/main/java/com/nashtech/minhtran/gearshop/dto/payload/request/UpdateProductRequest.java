package com.nashtech.minhtran.gearshop.dto.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private String name;

    private String description;

    private int manufacturerId = 0;

    private int categoryId = 0;
}
