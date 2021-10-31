package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.dto.ProductDetailDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.ProductDetailRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
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
import java.util.List;
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
    public ResponseEntity<?> getAllProductDetails (@RequestParam Optional<Integer> productId){
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            List<ProductDetailDTO> result = productService.getAllProductDetail(productId);
            response = ResponseEntity.ok().body(result);
        } catch (ProductNotExistException e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @PostMapping("/productDetail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewProductDetail(@Valid @RequestBody ProductDetailRequest productDetailRequest){
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            MessageResponse messageResponse = productService.addNewProductDetail(productDetailRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductNotExistException e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @PutMapping("/productDetail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProductDetail (@PathVariable int id, @Valid @RequestBody ProductDetailRequest productDetailRequest){
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            MessageResponse messageResponse = productService.updateProductDetail(id, productDetailRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductDetailNotExistException | ProductNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/productDetail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProductDetail(@PathVariable int id){
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            MessageResponse messageResponse = productService.deleteProductDetail(id);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (ProductDetailNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }
}
