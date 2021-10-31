package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.ProductDetailDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.model.ProductDetail;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDetailDTO> getAllProductDetail(Optional<Integer> productId);
    MessageResponse addNewProductDetail(@Valid ProductDetailRequest productDetailRequest);
    MessageResponse updateProductDetail(int id, @Valid ProductDetailRequest productDetailRequest);
    MessageResponse deleteProductDetail(int id);
}
