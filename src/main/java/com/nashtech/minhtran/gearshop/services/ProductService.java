package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.ProductDetailDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.RetrieveProductDetailException;
import com.nashtech.minhtran.gearshop.model.Product;
import com.nashtech.minhtran.gearshop.model.ProductDetail;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ResponseDTO getAllProductDetail(Optional<Integer> productId) throws RetrieveProductDetailException;
    ResponseDTO addNewProductDetail(@Valid ProductDetailRequest productDetailRequest);
    ResponseDTO updateProductDetail(int id, @Valid ProductDetailRequest productDetailRequest);
    ResponseDTO deleteProductDetail(int id);
    ResponseDTO getAllProducts(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    ResponseDTO addNewProduct(@Valid ProductRequest productRequest);
    ResponseDTO updateProduct(int id, UpdateProductRequest updateProductRequest);
    ResponseDTO deleteProduct(int id);
}
