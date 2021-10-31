package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.ProductDetailDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.EmptyProductIdException;
import com.nashtech.minhtran.gearshop.exception.ProductDetailNotExistException;
import com.nashtech.minhtran.gearshop.exception.ProductNotExistException;
import com.nashtech.minhtran.gearshop.model.Product;
import com.nashtech.minhtran.gearshop.model.ProductDetail;
import com.nashtech.minhtran.gearshop.repo.ProductDetailRepository;
import com.nashtech.minhtran.gearshop.repo.ProductRepository;
import com.nashtech.minhtran.gearshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ProductDetailDTO> getAllProductDetail(Optional<Integer> productId) {
        List<ProductDetailDTO> result = new ArrayList<>();
        if (productId.isPresent()){
            Product product = productRepository.findById(productId.get()).orElseThrow(ProductNotExistException::new);
            List<ProductDetail> productDetails = productDetailRepository.findByProduct(product);
            result = productDetails.stream().map(productDetail -> mapper.map(productDetail, ProductDetailDTO.class)).collect(Collectors.toList());
        } else {
            List<ProductDetail> productDetails = productDetailRepository.findAll();
            result = productDetails.stream().map(productDetail -> mapper.map(productDetail, ProductDetailDTO.class)).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public MessageResponse addNewProductDetail(@Valid ProductDetailRequest productDetailRequest) {
        if (productDetailRequest.getProductId() == 0){
            throw new EmptyProductIdException("Product must not be empty");
        }
        Product product = productRepository.findById(productDetailRequest.getProductId()).orElseThrow(ProductNotExistException::new);
        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .id(0)
                .color(productDetailRequest.getColor())
                .imageUrl(productDetailRequest.getImageUrl())
                .size(productDetailRequest.getSize())
                .price(productDetailRequest.getPrice())
                .quantity(productDetailRequest.getQuantity()).build();
        productDetailRepository.save(productDetail);
        return new MessageResponse("Add successful", HttpStatus.OK.value());
    }

    @Override
    public MessageResponse updateProductDetail(int id, @Valid ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(ProductDetailNotExistException::new);
        if (productDetailRequest.getProductId() != 0){
            Product product = productRepository.findById(productDetailRequest.getProductId()).orElseThrow(ProductNotExistException::new);
            ProductDetailDTO productDetailDTO = ProductDetailDTO.builder()
                    .product(product)
                    .color(productDetailRequest.getColor())
                    .id(id)
                    .imageUrl(productDetailRequest.getImageUrl())
                    .size(productDetailRequest.getSize())
                    .price(productDetailRequest.getPrice())
                    .quantity(productDetailRequest.getQuantity())
                    .build();
            productDetail = mapper.map(productDetailDTO, ProductDetail.class);
        } else {
            ProductDetailDTO productDetailDTO = ProductDetailDTO.builder()
                    .color(productDetailRequest.getColor())
                    .id(id)
                    .imageUrl(productDetailRequest.getImageUrl())
                    .size(productDetailRequest.getSize())
                    .price(productDetailRequest.getPrice())
                    .quantity(productDetailRequest.getQuantity())
                    .build();
            productDetail = mapper.map(productDetailDTO, ProductDetail.class);
        }
        productDetailRepository.save(productDetail);
        return new MessageResponse("update successful", HttpStatus.OK.value());
    }

    @Override
    public MessageResponse deleteProductDetail(int id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(ProductDetailNotExistException::new);
        productDetailRepository.delete(productDetail);
        return new MessageResponse("delete successful", HttpStatus.OK.value());
    }
}
