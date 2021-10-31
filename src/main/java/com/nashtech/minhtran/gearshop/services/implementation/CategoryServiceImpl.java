package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.CategoryDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.CategoryRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.CategoryNotExistException;
import com.nashtech.minhtran.gearshop.exception.EmptyBodyException;
import com.nashtech.minhtran.gearshop.exception.EmptyNameCategoryException;
import com.nashtech.minhtran.gearshop.model.Category;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import com.nashtech.minhtran.gearshop.repo.CategoryRepository;
import com.nashtech.minhtran.gearshop.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public Page<CategoryDTO> getAllCategory(Optional<Integer> page,
                                         Optional<Integer> size,
                                         Optional<String> sort,
                                         Optional<String> direction,
                                         Optional<String> name) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()){
            if (direction.get().equalsIgnoreCase("desc")){
                sortDirection = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest
                .of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<Category> categories;
        Page<CategoryDTO> result;
        if(name.isPresent()){
            categories = categoryRepository.findByName(name.get(), pageable);
            List<CategoryDTO> list = categories.stream().map(category -> mapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
            result = new PageImpl<>(list, pageable, list.size());
        } else {
            categories = categoryRepository.findAll(pageable);
            List<CategoryDTO> list = categories.stream().map(category -> mapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
            result = new PageImpl<>(list, pageable, list.size());
        }
        return result;
    }

    @Override
    public MessageResponse addNewCategory(@Valid CategoryRequest categoryRequest) {
        if(categoryRequest == null){
            throw new EmptyBodyException("Body cannot be null");
        } else if (categoryRequest.getName().isEmpty()){
            throw new EmptyNameCategoryException("Name cannot be null");
        } else if (categoryRequest.getParentCategoryId() != 0){
            Category parentCategory = categoryRepository
                    .findById(categoryRequest.getParentCategoryId())
                    .orElse(null);
            if(parentCategory != null){
                Category category = Category.builder()
                        .name(categoryRequest.getName())
                        .category(parentCategory)
                        .id(0).build();
                categoryRepository.save(category);
            } else {
                throw new CategoryNotExistException("Category isn't existed");
            }
        } else {
            Category category = Category.builder()
                    .id(0)
                    .name(categoryRequest.getName())
                    .build();
            categoryRepository.save(category);
        }

        return new MessageResponse("Add successful", HttpStatus.OK.value());
    }

    @Override
    public MessageResponse updateCategory(int id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new CategoryNotExistException("Category not exist");
        } else if (categoryRequest == null) {
            throw new EmptyBodyException("Body cannot be null");
        } else if (categoryRequest.getName().isEmpty()) {
            throw new EmptyNameCategoryException("Name cannot be empty");
        } else if (categoryRequest.getParentCategoryId() != 0) {
            Category parentCategory = categoryRepository
                    .findById(categoryRequest.getParentCategoryId())
                    .orElse(null);
            if (parentCategory != null) {
                category.setCategory(parentCategory);
                category.setName(categoryRequest.getName());
                categoryRepository.save(category);
            } else {
                throw new CategoryNotExistException("Category isn't existed");
            }
        } else {
            category.setName(categoryRequest.getName());
            categoryRepository.save(category);
        }

        return new MessageResponse("Update successful", HttpStatus.OK.value());
    }
    @Override
    public MessageResponse deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new CategoryNotExistException("Category not existed");
        }
        categoryRepository.delete(category);
        return new MessageResponse("delete successful", HttpStatus.OK.value());
    }
}
