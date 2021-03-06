package com.nashtech.minhtran.gearshop.dto;

import com.nashtech.minhtran.gearshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {

    private int id;

    private String color;

    @Min(value = 0)
    private double price;

    private String size;

    private String imageUrl;

    @NotBlank
    @Min(value = 0)
    private int quantity;

    @NotNull
    private Product product;
}
