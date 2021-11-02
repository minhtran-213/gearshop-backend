package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.api.admin.CategoryControllerAdmin;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.RetrieveCategoriesException;
import com.nashtech.minhtran.gearshop.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@SecurityRequirement(name = "minhtran")
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
}