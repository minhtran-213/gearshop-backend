package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.constants.SuccessCode;
import com.nashtech.minhtran.gearshop.dto.*;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.PageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ProductResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
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
import com.nashtech.minhtran.gearshop.util.converter.ProductConverter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    @Autowired
    ProductConverter productConverter;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Override
    public ResponseDTO getAllProductDetailByProduct(Optional<Integer> productId) throws RetrieveProductDetailException, ProductNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<BasicProductDetailDTO> result;
        if (productId.isPresent()) {
            try {
                Product product = productRepository.findById(productId.get())
                        .orElseThrow(() -> new ProductNotExistException(ErrorCode.ERROR_PRODUCT_NOT_EXIST));
                List<ProductDetail> productDetails = productDetailRepository.findByProduct(product);
                try {
                    result = productConverter.convertToBasicProductDetailDTO(productDetails);
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductDetailException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductDetailException(ErrorCode.ERROR_RETRIEVE_PRODUCT_DETAIL);
            }
        } else {
            try {
                List<ProductDetail> productDetails = productDetailRepository.findAll();
                try {
                    result = productConverter.convertToBasicProductDetailDTO(productDetails);
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductDetailException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductDetailException(ErrorCode.ERROR_RETRIEVE_PRODUCT_DETAIL);
            }
        }

        responseDTO.setTime(new Date());
        responseDTO.setObject(result);
        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_PRODUCT_DETAIL_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO addNewProductDetail(@Valid ProductDetailRequest productDetailRequest) throws EmptyProductIdException, ProductNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        if (productDetailRequest.getProductId() == 0) {
            logger.error(ErrorCode.ERROR_PRODUCT_ID_EMPTY);
            throw new EmptyProductIdException(ErrorCode.ERROR_PRODUCT_ID_EMPTY);
        }
        Product product = productRepository.findById(productDetailRequest.getProductId()).orElseThrow(ProductNotExistException::new);
        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .id(-1)
                .color(productDetailRequest.getColor())
                .imageUrl(productDetailRequest.getImageUrl())
                .size(productDetailRequest.getSize())
                .price(productDetailRequest.getPrice())
                .quantity(productDetailRequest.getQuantity()).build();
        try {
            productDetailRepository.save(productDetail);
            responseDTO.setSuccessCode(SuccessCode.ADD_PRODUCT_DETAILS_SUCCESS);
            responseDTO.setObject(true);
            responseDTO.setTime(new Date());
            return responseDTO;
        } catch (SaveProductException e) {
            logger.error(e.getMessage());
            throw new SaveProductException();
        }
    }

    @Override
    public ResponseDTO updateProductDetail(int id, @Valid ProductDetailRequest productDetailRequest) throws ProductDetailNotExistException, ProductNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new ProductDetailNotExistException(ErrorCode.ERROR_PRODUCT_DETAIL_NOT_EXIST));
        if (productDetailRequest.getProductId() != 0) {
            Product product = productRepository.findById(productDetailRequest.getProductId())
                    .orElseThrow(() -> new ProductNotExistException(ErrorCode.ERROR_PRODUCT_NOT_EXIST));
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
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.UPDATE_PRODUCT_DETAILS_SUCCESS);
        responseDTO.setObject(true);
        return responseDTO;
    }

    @Override
    public ResponseDTO deleteProductDetail(int id) throws ProductDetailNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(() -> new ProductDetailNotExistException(ErrorCode.ERROR_PRODUCT_DETAIL_NOT_EXIST));
        productDetailRepository.delete(productDetail);
        responseDTO.setSuccessCode(SuccessCode.DELETE_PRODUCT_DETAILS_SUCCESS);
        responseDTO.setTime(new Date());
        responseDTO.setObject(true);
        return responseDTO;
    }

    @Override
    public ResponseDTO getAllProductsPaging(Optional<Integer> page,
                                            Optional<Integer> size,
                                            Optional<String> sort,
                                            Optional<String> direction, Optional<String> name) throws RetrieveProductException {
        ResponseDTO responseDTO = new ResponseDTO();
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest
                .of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<Product> products;
        Page<ProductDTO> result;
        if (name.isPresent()) {
            try {
                products = productRepository.findByName(name.get(), pageable);
                try {
                    List<ProductDTO> productDTOS = productConverter.convertEntitiesToDTOs(products);
                    result = new PageImpl<>(productDTOS, pageable, productDTOS.size());
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
            }
        } else {
            try {
                products = productRepository.findAll(pageable);
                try {
                    List<ProductDTO> productDTOS = productConverter.convertEntitiesToDTOs(products);
                    result = new PageImpl<>(productDTOS, pageable, productDTOS.size());
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
            }
        }
        PageResponse response = PageResponse.builder()
                .content(result.getContent())
                .currentPage(result.getNumber())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages()).build();
        responseDTO.setObject(response);
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_PRODUCTS_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO addNewProduct(@Valid ProductRequest productRequest) throws CategoryNotExistException, ManufacturerNotExistException, EmptyBodyException {
        ResponseDTO responseDTO = new ResponseDTO();
        if (productRequest == null) {
            throw new EmptyBodyException(ErrorCode.EMPTY_BODY);
        }
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST));
        Manufacturer manufacturer = manufacturerRepository.findById(productRequest.getManufacturerId())
                .orElseThrow(() -> new ManufacturerNotExistException(ErrorCode.ERROR_MANUFACTURER_NOT_EXIST));
        Product product = Product.builder()
                .name(productRequest.getName())
                .createdDate(new Date())
                .description(productRequest.getDescription())
                .manufacturer(manufacturer)
                .category(category)
                .id(-1)
                .build();
        try {
            productRepository.save(product);
        } catch (SaveProductException e) {
            logger.error(e.getMessage());
            throw new SaveProductException();
        }
        responseDTO.setObject(true);
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.ADD_PRODUCT_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO updateProduct(int id, @Valid UpdateProductRequest updateProductRequest) throws ProductNotExistException, CategoryNotExistException, ManufacturerNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistException(ErrorCode.ERROR_PRODUCT_NOT_EXIST));
        Category category = categoryRepository.findById(updateProductRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST));
        Manufacturer manufacturer = manufacturerRepository.findById(updateProductRequest.getManufacturerId())
                .orElseThrow(() -> new ManufacturerNotExistException(ErrorCode.ERROR_MANUFACTURER_NOT_EXIST));
        product.setManufacturer(manufacturer);
        product.setCategory(category);
        product.setName(updateProductRequest.getName());
        product.setDescription(updateProductRequest.getDescription());
        product.setUpdatedDate(new Date());
        try {
            productRepository.save(product);
        } catch (SaveProductException e) {
            logger.error(e.getMessage());
            throw new SaveProductException();
        }
        responseDTO.setSuccessCode(SuccessCode.UPDATE_PRODUCT_SUCCESS);
        responseDTO.setTime(new Date());
        responseDTO.setObject(true);
        return responseDTO;
    }

    @Override
    public ResponseDTO deleteProduct(int id) {
        ResponseDTO responseDTO = new ResponseDTO();
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistException("Product not exist"));
        productRepository.delete(product);
        responseDTO.setObject(true);
        responseDTO.setSuccessCode(SuccessCode.DELETE_PRODUCT_SUCCESS);
        responseDTO.setTime(new Date());
        return responseDTO;
    }

    @Override
    public ResponseDTO userGetAllProducts(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name) {
        ResponseDTO responseDTO = new ResponseDTO();
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest
                .of(page.orElse(0), size.orElse(8), sortDirection, sort.orElse("id"));
        Page<Product> products;
        Page<ProductResponse> result;
        if (name.isPresent()) {
            try {
                products = productRepository.findByName(name.get(), pageable);
                try {
                    List<ProductResponse> productDTOS = productConverter.convertToResponses(products);
                    result = new PageImpl<>(productDTOS, pageable, productDTOS.size());
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
            }
        } else {
            try {
                products = productRepository.findAll(pageable);
                try {
                    List<ProductResponse> productDTOS = productConverter.convertToResponses(products);
                    result = new PageImpl<>(productDTOS, pageable, productDTOS.size());
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
            }
        }
        PageResponse response = PageResponse.builder()
                .content(result.getContent())
                .currentPage(result.getNumber())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages()).build();
        responseDTO.setObject(response);
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_PRODUCTS_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO getUserProductDetails(int productId) throws ProductNotExistException, RetrieveProductDetailException {
        List<UserProductDetailDTO> result;

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotExistException(ErrorCode.ERROR_PRODUCT_NOT_EXIST));
            List<ProductDetail> productDetails = productDetailRepository.findByProduct(product);
            try {
                result = productConverter.convertToListOfUserProductDetail(productDetails);

            } catch (ConvertDTOException e) {
                logger.error(e.getMessage());
                throw new ConvertDTOException();
            }
        } catch (RetrieveProductDetailException e) {
            logger.error(e.getMessage());
            throw new RetrieveProductDetailException(ErrorCode.ERROR_RETRIEVE_PRODUCT_DETAIL);
        }

        return new ResponseDTO(SuccessCode.RETRIEVE_PRODUCT_DETAIL_SUCCESS, result);
    }

    @Override
    public ResponseDTO getProductsByManufacturerId(int id, Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction) throws RetrieveProductException {
        Page<UserProductDTO> result = new PageImpl<>(new ArrayList<>());
        try {
            Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() -> new ManufacturerNotExistException(ErrorCode.ERROR_MANUFACTURER_NOT_EXIST));
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (direction.isPresent()) {
                if (direction.get().equalsIgnoreCase("desc")) {
                    sortDirection = Sort.Direction.DESC;
                }
            }
            Pageable pageable = PageRequest
                    .of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
            try {
                Page<Product> products = productRepository.findByManufacturer(manufacturer, pageable);
                try {
                    List<UserProductDTO> list = productConverter.convertToUserDTO(products);
                    result = new PageImpl<>(list, pageable, list.size());
                } catch (ConvertDTOException e) {
                    throw new ConvertDTOException();
                }
            } catch (RetrieveProductException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
            }
        } catch (RetrieveProductException e) {
            logger.error(e.getMessage());
            throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
        }

        return new ResponseDTO(SuccessCode.RETRIEVE_PRODUCTS_SUCCESS, result);
    }

    @Override
    public ResponseDTO getProductsByCategoryId(int id, Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction) throws RetrieveProductException {
        Page<ProductResponse> result = new PageImpl<>(new ArrayList<>());
        PageResponse response;
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST));
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (direction.isPresent()) {
                if (direction.get().equalsIgnoreCase("desc")) {
                    sortDirection = Sort.Direction.DESC;
                }
            }
            Pageable pageable = PageRequest
                    .of(page.orElse(0), size.orElse(8), sortDirection, sort.orElse("id"));
            try {
                Page<Product> products = productRepository.findByCategory(category, pageable);
                try {
                    List<ProductResponse> list = productConverter.convertToResponses(products);
                    result = new PageImpl<>(list, pageable, list.size());
                } catch (ConvertDTOException e){
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
                response = PageResponse.builder()
                        .content(result.getContent())
                        .currentPage(result.getNumber())
                        .totalElements(products.getTotalElements())
                        .totalPages(products.getTotalPages()).build();
            } catch (RetrieveProductException e) {
                logger.error(e.getMessage());
                throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
            }
        } catch (RetrieveProductException e) {
            logger.error(e.getMessage());
            throw new RetrieveProductException(ErrorCode.ERROR_RETRIEVE_PRODUCTS);
        }

        return new ResponseDTO(SuccessCode.RETRIEVE_PRODUCTS_SUCCESS, response);
    }

    @Override
    public ResponseDTO getProductById(int id) throws ProductNotExistException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotExistException(ErrorCode.ERROR_PRODUCT_NOT_EXIST));
        AdminProductDTO productDTO = productConverter.convertToAdminDTO(product);
        return new ResponseDTO("Return product success", productDTO);
    }
}
