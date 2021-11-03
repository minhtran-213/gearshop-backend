package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicProductDetailDTO {
    private int id;

    private String color;

    private double price;

    private String size;

    private String imageUrl;

    private int quantity;
}
