package com.nashtech.minhtran.gearshop.dto;

import com.nashtech.minhtran.gearshop.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private int id;

    @NotBlank
    private String name;

    private Category category;
}
