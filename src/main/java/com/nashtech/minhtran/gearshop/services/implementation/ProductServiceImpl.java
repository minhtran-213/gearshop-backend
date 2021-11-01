package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.ProductDetailDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.*;
import com.nashtech.minhtran.gearshop.model.Category;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import com.nashtech.minhtran.gearshop.model.Product;
import com.nashtech.minhtran.gearshop.model.ProductDetail;
import com.nashtech.minhtran.gearshop.repo.CategoryRepository;
import com.nashtech.minhtran.gearshop.repo.ManufacturerRepository;
import com.nashtech.minhtran.gearshop.repo.ProductDetailRepository;
import com.nashtech.minhtran.gearshop.repo.ProductRepository;
import com.nashtech.minhtran.gearshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
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
    CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ProductDetailDTO> getAllProductDetail(Optional<Integer> productId) {
        List<ProductDetailDTO> result = new ArrayList<>();
        if (productId.isPresent()) {
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
        if (productDetailRequest.getProductId() == 0) {
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
        if (productDetailRequest.getProductId() != 0) {
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

    @Override
    public Page<Product> getAllProducts(Optional<Integer> page,
                                        Optional<Integer> size,
                                        Optional<String> sort,
                                        Optional<String> direction, Optional<String> name) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest
                .of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<Product> products;
        if (name.isPresent()) {
            products = productRepository.findByName(name.get(), pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products;
    }

    @Override
    public MessageResponse addNewProduct(@Valid ProductRequest productRequest) {
        if (productRequest == null) {
            throw new EmptyBodyException("Body cannot be null");
        }
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new CategoryNotExistException("Category not existed!"));
        Manufacturer manufacturer = manufacturerRepository.findById(productRequest.getManufacturerId()).orElseThrow(() -> new ManufacturerNotExistException("Manufacturer not exist"));
        Product product = Product.builder()
                .name(productRequest.getName())
                .createdDate(new Date())
                .description(productRequest.getDescription())
                .manufacturer(manufacturer)
                .category(category)
                .build();
        productRepository.save(product);
        return new MessageResponse("add successful", HttpStatus.OK.value());
    }

    @Override
    public MessageResponse updateProduct(int id, @Valid UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistException("Product not exist"));
        Category category = categoryRepository.findById(updateProductRequest.getCategoryId()).orElseThrow(() -> new CategoryNotExistException("Category not existed!"));
        Manufacturer manufacturer = manufacturerRepository.findById(updateProductRequest.getManufacturerId()).orElseThrow(() -> new ManufacturerNotExistException("Manufacturer not exist"));
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setName(updateProductRequest.getName());
        product.setDescription(updateProductRequest.getDescription());
        product.setUpdatedDate(new Date());
        productRepository.save(product);
        return new MessageResponse("update successful", HttpStatus.OK.value());
    }

    @Override
    public MessageResponse deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistException("Product not exist"));
        productRepository.delete(product);
        return new MessageResponse("delete successful", HttpStatus.OK.value());
    }
}
