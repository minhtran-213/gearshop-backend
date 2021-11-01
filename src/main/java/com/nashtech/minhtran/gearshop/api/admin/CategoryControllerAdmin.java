package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.dto.CategoryDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.CategoryRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.CategoryNotExistException;
import com.nashtech.minhtran.gearshop.exception.EmptyBodyException;
import com.nashtech.minhtran.gearshop.exception.EmptyNameCategoryException;
import com.nashtech.minhtran.gearshop.model.Category;
import com.nashtech.minhtran.gearshop.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "minhtran")
public class CategoryControllerAdmin {

    @Autowired
    CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryControllerAdmin.class);

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllCategories(@RequestParam Optional<Integer> page,
                                                        @RequestParam Optional<Integer> size,
                                                        @RequestParam Optional<String> sort,
                                                        @RequestParam Optional<String> direction,
                                                        @RequestParam Optional<String> name){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = categoryService.getAllCategory(page, size, sort, direction, name);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewCategory(@RequestBody CategoryRequest categoryRequest){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = categoryService.addNewCategory(categoryRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (EmptyBodyException | EmptyNameCategoryException | CategoryNotExistException e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateCategory(@PathVariable int id, @RequestBody @Valid CategoryRequest categoryRequest){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = categoryService.updateCategory(id, categoryRequest);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (EmptyBodyException | EmptyNameCategoryException | CategoryNotExistException e){
            logger.error(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory (@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = categoryService.deleteCategory(id);
            response = ResponseEntity.ok().body(messageResponse);
        } catch (CategoryNotExistException e){
            logger.error(e.getMessage());
        }
        return response;
    }
}
