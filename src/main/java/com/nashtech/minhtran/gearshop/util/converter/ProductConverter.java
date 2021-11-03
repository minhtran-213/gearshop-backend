package com.nashtech.minhtran.gearshop.util.converter;

import com.nashtech.minhtran.gearshop.dto.ProductDTO;
import com.nashtech.minhtran.gearshop.dto.UserProductDTO;
import com.nashtech.minhtran.gearshop.dto.UserProductDetailDTO;
import com.nashtech.minhtran.gearshop.model.Product;
import com.nashtech.minhtran.gearshop.model.ProductDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {
    @Autowired
    ModelMapper modelMapper;

    public ProductDTO convertEntityToDTO(Product product){
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategoryName(product.getCategory().getName());
        productDTO.setManufacturerName(product.getManufacturer().getName());
        return productDTO;
    }

    public UserProductDTO convertToUserDTO (Product product){
        UserProductDTO productDTO = modelMapper.map(product, UserProductDTO.class);
        productDTO.setManufacturerName(product.getManufacturer().getName());
        return productDTO;
    }

    public UserProductDetailDTO convertToUserProductDetailDTO(ProductDetail productDetail){
        UserProductDetailDTO productDetailDTO = modelMapper.map(productDetail, UserProductDetailDTO.class);
        productDetailDTO.setProductDescription(productDetail.getProduct().getDescription());
        productDetailDTO.setProductName(productDetail.getProduct().getName());
        productDetailDTO.setProductManufacturer(productDetail.getProduct().getManufacturer().getName());
        return productDetailDTO;
    }

    public List<UserProductDetailDTO> convertToListOfUserProductDetail(Page<ProductDetail> productDetails){
        return productDetails.stream().map(this::convertToUserProductDetailDTO).collect(Collectors.toList());
    }

    public List<UserProductDetailDTO> convertToListOfUserProductDetail(List<ProductDetail> productDetails){
        return productDetails.stream().map(this::convertToUserProductDetailDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> convertEntitiesToDTOs(Page<Product> products){
        return products.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public List<UserProductDTO> convertToUserDTO (Page<Product> products){
        return products.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }
}
