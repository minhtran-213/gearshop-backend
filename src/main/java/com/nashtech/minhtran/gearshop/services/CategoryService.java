package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.payload.request.CategoryRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.CategoryNotExistException;
import com.nashtech.minhtran.gearshop.exception.RetrieveCategoriesException;
import com.nashtech.minhtran.gearshop.exception.RetrieveSingleCategoryException;

import javax.validation.Valid;
import java.util.Optional;

public interface CategoryService {
    ResponseDTO getAllCategoryPaging(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    ResponseDTO getSubCategoriesByParentCategories(int id) throws RetrieveCategoriesException, CategoryNotExistException;
    ResponseDTO addNewCategory (@Valid CategoryRequest categoryRequest);
    ResponseDTO updateCategory (int id, @Valid CategoryRequest categoryRequest);
    ResponseDTO deleteCategory (int id);
    ResponseDTO getAllCategories() throws RetrieveCategoriesException;
    ResponseDTO getCategory(int id) throws RetrieveSingleCategoryException, CategoryNotExistException;
    ResponseDTO getAllParentCategory(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction) throws RetrieveCategoriesException;
    ResponseDTO getAllCategoriesForAdmin() throws RetrieveCategoriesException;
}
