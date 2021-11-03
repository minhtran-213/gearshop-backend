package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductDetailDTO {
    private int id;
    private String color;
    private double price;
    private String size;
    private String imageUrl;
    private int quantity;
    private String productName;
    private String productDescription;
    private String productManufacturer;
}
