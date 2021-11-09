package com.nashtech.minhtran.gearshop.dto.payload.response;

import com.nashtech.minhtran.gearshop.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private String manufacturerName;
    private Collection<ProductDetailResponse> productDetail;
}
