package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateProductRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.CategoryNotExistException;
import com.nashtech.minhtran.gearshop.exception.ManufacturerNotExistException;
import com.nashtech.minhtran.gearshop.exception.ProductDetailNotExistException;
import com.nashtech.minhtran.gearshop.exception.ProductNotExistException;
import com.nashtech.minhtran.gearshop.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "minhtran")
public class ProductControllerAdmin {

    @Autowired
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductControllerAdmin.class);

    @GetMapping("/productDetails")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllProductDetails (@RequestParam Optional<Integer> productId){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = productService.getAllProductDetail(productId);
            response = ResponseEntity.ok().body(result);
        } catch (ProductNotExistException e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @PostMapping("/productDetail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewProductDetail(@Valid @RequestBody ProductDetailRequest productDetailRequest){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = productService.addNewProductDetail(productDetailRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductNotExistException e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @PutMapping("/productDetail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateProductDetail (@PathVariable int id, @Valid @RequestBody ProductDetailRequest productDetailRequest){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = productService.updateProductDetail(id, productDetailRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductDetailNotExistException | ProductNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/productDetail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteProductDetail(@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = productService.deleteProductDetail(id);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductDetailNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllProducts(@RequestParam Optional<Integer> page,
                                            @RequestParam Optional<Integer> size,
                                            @RequestParam Optional<String> sort,
                                            @RequestParam Optional<String> direction,
                                            @RequestParam Optional<String> name){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO products = productService.getAllProductsPaging(page, size, sort, direction, name);
            response = ResponseEntity.ok().body(products);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewProduct(@RequestBody ProductRequest productRequest){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = productService.addNewProduct(productRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (CategoryNotExistException | ManufacturerNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable int id, @RequestBody UpdateProductRequest updateProductRequest){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = productService.updateProduct(id, updateProductRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (CategoryNotExistException | ManufacturerNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = productService.deleteProduct(id);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }
}
