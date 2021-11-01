package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDTO {
    private int id;

    @NotBlank
    private String name;
}
