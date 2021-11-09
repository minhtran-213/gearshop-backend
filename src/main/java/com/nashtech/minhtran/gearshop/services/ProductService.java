package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.ProductNotExistException;
import com.nashtech.minhtran.gearshop.exception.RetrieveProductDetailException;
import com.nashtech.minhtran.gearshop.exception.RetrieveProductException;

import javax.validation.Valid;
import java.util.Optional;

public interface ProductService {
    ResponseDTO getAllProductDetailByProduct (Optional<Integer> productId) throws RetrieveProductDetailException;
    ResponseDTO addNewProductDetail(@Valid ProductDetailRequest productDetailRequest);
    ResponseDTO updateProductDetail(int id, @Valid ProductDetailRequest productDetailRequest);
    ResponseDTO deleteProductDetail(int id);
    ResponseDTO getAllProductsPaging(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    ResponseDTO addNewProduct(@Valid ProductRequest productRequest);
    ResponseDTO updateProduct(int id, UpdateProductRequest updateProductRequest);
    ResponseDTO deleteProduct(int id);
    ResponseDTO userGetAllProducts(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    ResponseDTO getUserProductDetails(int productId) throws ProductNotExistException, RetrieveProductDetailException;
    //TODO: add get product by manufacturerId and cateId
    ResponseDTO getProductsByManufacturerId(int id, Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction) throws RetrieveProductException;
    ResponseDTO getProductsByCategoryId(int id, Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction) throws RetrieveProductException;
    ResponseDTO getProductById(int id) throws ProductNotExistException;
}
