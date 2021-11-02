package com.nashtech.minhtran.gearshop.util.converter;

import com.nashtech.minhtran.gearshop.dto.ProductDTO;
import com.nashtech.minhtran.gearshop.model.Product;
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

    public List<ProductDTO> convertEntitiesToDTOs(Page<Product> products){
        return products.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }
}
