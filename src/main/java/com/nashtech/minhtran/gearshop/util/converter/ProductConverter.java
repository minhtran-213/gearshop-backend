package com.nashtech.minhtran.gearshop.util.converter;

import com.nashtech.minhtran.gearshop.dto.*;
import com.nashtech.minhtran.gearshop.dto.payload.response.ProductDetailResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ProductResponse;
import com.nashtech.minhtran.gearshop.exception.ConvertDTOException;
import com.nashtech.minhtran.gearshop.model.Product;
import com.nashtech.minhtran.gearshop.model.ProductDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {
    @Autowired
    ModelMapper modelMapper;

    public ProductDTO convertEntityToDTO(Product product) throws ConvertDTOException {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategoryName(product.getCategory().getName());
        productDTO.setManufacturerName(product.getManufacturer().getName());
        return productDTO;
    }

    public UserProductDTO convertToUserDTO (Product product) throws ConvertDTOException{
        UserProductDTO productDTO = modelMapper.map(product, UserProductDTO.class);
        productDTO.setManufacturerName(product.getManufacturer().getName());
        return productDTO;
    }

    public ProductResponse convertToResponse (Product product) throws ConvertDTOException {
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        Collection<ProductDetail> productDetails = product.getProductDetails();
        Collection<ProductDetailResponse> productDetailResponses = productDetails.stream()
                .map(pd -> modelMapper.map(pd, ProductDetailResponse.class))
                .collect(Collectors.toList());
        productResponse.setProductDetail(productDetailResponses);
        return productResponse;
    }
    public List<ProductResponse> convertToResponses (Page<Product> products) throws ConvertDTOException{
        return products.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public AdminProductDTO convertToAdminDTO (Product product){
        AdminProductDTO productDTO = modelMapper.map(product, AdminProductDTO.class);
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setManufacturerId(product.getManufacturer().getId());
        return productDTO;
    }

    public UserProductDetailDTO convertToUserProductDetailDTO(ProductDetail productDetail) throws ConvertDTOException{
        UserProductDetailDTO productDetailDTO = modelMapper.map(productDetail, UserProductDetailDTO.class);
        productDetailDTO.setProductDescription(productDetail.getProduct().getDescription());
        productDetailDTO.setProductName(productDetail.getProduct().getName());
        productDetailDTO.setProductManufacturer(productDetail.getProduct().getManufacturer().getName());
        return productDetailDTO;
    }

    public List<UserProductDetailDTO> convertToListOfUserProductDetail(Page<ProductDetail> productDetails) throws ConvertDTOException{
        return productDetails.stream().map(this::convertToUserProductDetailDTO).collect(Collectors.toList());
    }

    public List<UserProductDetailDTO> convertToListOfUserProductDetail(List<ProductDetail> productDetails) throws ConvertDTOException{
        return productDetails.stream().map(this::convertToUserProductDetailDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> convertEntitiesToDTOs(Page<Product> products) throws ConvertDTOException{
        return products.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public List<UserProductDTO> convertToUserDTO (Page<Product> products) throws ConvertDTOException{
        return products.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    public List<BasicProductDetailDTO> convertToBasicProductDetailDTO (List<ProductDetail> productDetails) throws ConvertDTOException{
        return  productDetails.stream()
                .map(productDetail -> modelMapper.map(productDetail, BasicProductDetailDTO.class))
                .collect(Collectors.toList());
    }
}
