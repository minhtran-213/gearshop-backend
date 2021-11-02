package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.constants.SuccessCode;
import com.nashtech.minhtran.gearshop.dto.CategoryBasicDTO;
import com.nashtech.minhtran.gearshop.dto.CategoryDTO;
import com.nashtech.minhtran.gearshop.dto.SingleCategoryDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.CategoryRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.*;
import com.nashtech.minhtran.gearshop.model.Category;
import com.nashtech.minhtran.gearshop.repo.CategoryRepository;
import com.nashtech.minhtran.gearshop.services.CategoryService;
import com.nashtech.minhtran.gearshop.util.converter.CategoryConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CategoryConverter categoryConverter;

    @Override
    public ResponseDTO getAllCategoryPaging(Optional<Integer> page,
                                            Optional<Integer> size,
                                            Optional<String> sort,
                                            Optional<String> direction,
                                            Optional<String> name) throws RetrieveCategoriesException {
        ResponseDTO responseDTO = new ResponseDTO();
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest
                .of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<Category> categories;
        Page<CategoryBasicDTO> result;
        if (name.isPresent()) {
            try {
                categories = categoryRepository.findByName(name.get(), pageable);
                List<CategoryBasicDTO> list = categoryConverter.convertCategoriesToBasicDTOs(categories);
                result = new PageImpl<>(list, pageable, list.size());
            } catch (Exception e) {
                throw new RetrieveCategoriesException(ErrorCode.ERROR_RETRIEVE_CATEGORIES);
            }
        } else {
            try {
                categories = categoryRepository.findAll(pageable);
                List<CategoryBasicDTO> list = categoryConverter.convertCategoriesToBasicDTOs(categories);
                result = new PageImpl<>(list, pageable, list.size());
            } catch (Exception e) {
                throw new RetrieveCategoriesException(ErrorCode.ERROR_RETRIEVE_CATEGORIES);
            }
        }
        responseDTO.setObject(result);
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_CATEGORIES_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO getSubCategoriesByParentCategories(int id) throws RetrieveCategoriesException, CategoryNotExistException {
        List<Category> categories;
        List<CategoryBasicDTO> result;
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST));
            try {
                categories = categoryRepository.findByCategory(category);
                result = categoryConverter.convertCategoriesToBasicDTOs(categories);
            } catch (Exception e) {
                throw new RetrieveCategoriesException(ErrorCode.ERROR_RETRIEVE_CATEGORIES);
            }
        } catch (Exception e) {
            throw new RetrieveCategoriesException(ErrorCode.ERROR_RETRIEVE_CATEGORIES);
        }
        return new ResponseDTO(SuccessCode.RETRIEVE_CATEGORIES_SUCCESS, result);
    }

    @Override
    public ResponseDTO addNewCategory(@Valid CategoryRequest categoryRequest) throws EmptyBodyException, EmptyNameCategoryException, CategoryNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        if (categoryRequest == null) {
            throw new EmptyBodyException(ErrorCode.EMPTY_BODY);
        } else if (categoryRequest.getName().isEmpty()) {
            throw new EmptyNameCategoryException("Name cannot be null");
        } else if (categoryRequest.getParentCategoryId() != 0) {
            Category parentCategory = categoryRepository
                    .findById(categoryRequest.getParentCategoryId())
                    .orElse(null);
            if (parentCategory != null) {
                Category category = Category.builder()
                        .name(categoryRequest.getName())
                        .category(parentCategory)
                        .id(0).build();
                categoryRepository.save(category);
            } else {
                throw new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_EMPTY_NAME);
            }
        } else {
            Category category = Category.builder()
                    .id(0)
                    .name(categoryRequest.getName())
                    .build();
            categoryRepository.save(category);
        }
        responseDTO.setSuccessCode(SuccessCode.ADD_CATEGORY_SUCCESS);
        responseDTO.setObject(true);
        responseDTO.setTime(new Date());
        return responseDTO;
    }

    @Override
    public ResponseDTO updateCategory(int id, CategoryRequest categoryRequest) throws CategoryNotExistException, EmptyBodyException, EmptyNameCategoryException {
        ResponseDTO responseDTO = new ResponseDTO();
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST);
        } else if (categoryRequest == null) {
            throw new EmptyBodyException(ErrorCode.EMPTY_BODY);
        } else if (categoryRequest.getName().isEmpty()) {
            throw new EmptyNameCategoryException(ErrorCode.ERROR_CATEGORY_EMPTY_NAME);
        } else if (categoryRequest.getParentCategoryId() != 0) {
            Category parentCategory = categoryRepository
                    .findById(categoryRequest.getParentCategoryId())
                    .orElse(null);
            if (parentCategory != null) {
                category.setCategory(parentCategory);
                category.setName(categoryRequest.getName());
                categoryRepository.save(category);
            } else {
                throw new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST);
            }
        } else {
            category.setName(categoryRequest.getName());
            categoryRepository.save(category);
        }
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.UPDATE_CATEGORY_SUCCESS);
        responseDTO.setObject(true);
        return responseDTO;
    }

    @Override
    public ResponseDTO deleteCategory(int id) throws CategoryNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST);
        }
        categoryRepository.delete(category);
        responseDTO.setTime(new Date());
        responseDTO.setSuccessCode(SuccessCode.DELETE_CATEGORY_SUCCESS);
        responseDTO.setObject(true);
        return responseDTO;
    }

    @Override
    public ResponseDTO getAllCategories() throws RetrieveCategoriesException {
        List<CategoryDTO> result = new ArrayList<>();
        try {
            List<Category> categories = categoryRepository.findWhereParentIsNull();
            result = categoryConverter.convertCategoriesToDTOs(categories);
        } catch (Exception e) {
            throw new RetrieveCategoriesException(ErrorCode.ERROR_RETRIEVE_CATEGORIES);
        }
        return new ResponseDTO(SuccessCode.RETRIEVE_CATEGORIES_SUCCESS, result);
    }

    @Override
    public ResponseDTO getCategory(int id) throws RetrieveSingleCategoryException, CategoryNotExistException {
        SingleCategoryDTO categoryDTO = null;
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistException(ErrorCode.ERROR_CATEGORY_NOT_EXIST));
            categoryDTO = categoryConverter.convertEntityToSingleDTO(category);
        } catch (Exception e){
            throw new RetrieveCategoriesException(ErrorCode.ERROR_RETRIEVE_SINGLE_CATEGORY);
        }
        return new ResponseDTO(SuccessCode.RETRIEVE_SINGLE_CATEGORY_SUCCESS, categoryDTO);
    }
}
