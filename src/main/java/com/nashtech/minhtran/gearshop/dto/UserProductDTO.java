package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductDTO {
    private int id;
    private String name;
    private String description;
    private String manufacturerName;
}
