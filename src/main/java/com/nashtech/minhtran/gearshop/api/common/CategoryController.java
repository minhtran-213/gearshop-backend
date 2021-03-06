package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.CategoryNotExistException;
import com.nashtech.minhtran.gearshop.exception.RetrieveCategoriesException;
import com.nashtech.minhtran.gearshop.exception.RetrieveSingleCategoryException;
import com.nashtech.minhtran.gearshop.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/common")
@SecurityRequirement(name = "minhtran")
@CrossOrigin(origins = "*", maxAge = 30)
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO> getAllCategories(){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = categoryService.getAllCategories();
            response = ResponseEntity.ok().body(result);
        } catch (RetrieveCategoriesException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseDTO> getCategory(@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = categoryService.getCategory(id);
            response = ResponseEntity.ok().body(result);
        } catch (CategoryNotExistException | RetrieveSingleCategoryException e) {
            logger.error(e.getMessage());
            throw new CategoryNotExistException(e.getMessage());
        }
        return response;
    }
}