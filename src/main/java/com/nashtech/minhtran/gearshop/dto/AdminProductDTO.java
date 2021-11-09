package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDTO {
    private String name;
    private String description;
    private int manufacturerId;
    private int categoryId;
}
