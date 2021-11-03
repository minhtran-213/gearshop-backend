package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.ProductNotExistException;
import com.nashtech.minhtran.gearshop.exception.RetrieveManufacturerException;
import com.nashtech.minhtran.gearshop.exception.RetrieveProductDetailException;
import com.nashtech.minhtran.gearshop.exception.RetrieveProductException;
import com.nashtech.minhtran.gearshop.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@SecurityRequirement(name = "minhtran")
public class ProductController {

    @Autowired
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products")
    public ResponseEntity<ResponseDTO> getProducts(Optional<Integer> page,
                                                   Optional<Integer> size,
                                                   Optional<String> sort,
                                                   Optional<String> direction, Optional<String> name) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = productService.userGetAllProducts(page, size, sort, direction, name);
            response = ResponseEntity.ok().body(result);
        } catch (RetrieveManufacturerException e) {
            logger.error(e.getMessage());
        }
        return response;
    }


    @GetMapping("/productDetails")
    public ResponseEntity<ResponseDTO> getProductDetails(@RequestParam int productId) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseDTO responseDTO = productService.getUserProductDetails(productId);
        response = ResponseEntity.ok().body(responseDTO);
        return response;
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<ResponseDTO> getProductsByCategory(@PathVariable int id,
                                                             Optional<Integer> page,
                                                             Optional<Integer> size,
                                                             Optional<String> sort,
                                                             Optional<String> direction) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = productService.getProductsByCategoryId(id, page, size, sort, direction);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (RetrieveProductException e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    @GetMapping("/products/manufacturer/{id}")
    public ResponseEntity<ResponseDTO> getProductsByManufacturer(@PathVariable int id,
                                                             Optional<Integer> page,
                                                             Optional<Integer> size,
                                                             Optional<String> sort,
                                                             Optional<String> direction) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = productService.getProductsByManufacturerId(id, page, size, sort, direction);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (RetrieveProductException e) {
            logger.error(e.getMessage());
        }
        return response;
    }
}