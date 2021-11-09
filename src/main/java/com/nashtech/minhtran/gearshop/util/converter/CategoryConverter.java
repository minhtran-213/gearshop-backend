package com.nashtech.minhtran.gearshop.util.converter;

import com.nashtech.minhtran.gearshop.dto.CategoryBasicDTO;
import com.nashtech.minhtran.gearshop.dto.CategoryDTO;
import com.nashtech.minhtran.gearshop.dto.SingleCategoryDTO;
import com.nashtech.minhtran.gearshop.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    @Autowired
    ModelMapper modelMapper;

    public CategoryDTO convertEntityToDTO(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        Collection<Category> categories = category.getCategories();
        Collection<CategoryBasicDTO> categoryBasicDTOS = categories
                .stream()
                .map(c -> modelMapper.map(c, CategoryBasicDTO.class)).collect(Collectors.toList());
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setCategories(categoryBasicDTOS);
        return categoryDTO;
    }

    public SingleCategoryDTO convertEntityToSingleDTO(Category category) {
        SingleCategoryDTO categoryDTO = modelMapper.map(category, SingleCategoryDTO.class);
        if (category.getCategory() != null) {
            CategoryBasicDTO categoryBasicDTO = modelMapper.map(category.getCategory(), CategoryBasicDTO.class);
            categoryDTO.setParentCategory(categoryBasicDTO);
        } else {
            categoryDTO.setParentCategory(null);
        }
        return categoryDTO;
    }

    public List<CategoryDTO> convertCategoriesToDTOs(Page<Category> categories) {
        return categories.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public List<CategoryDTO> convertCategoriesToDTOs(List<Category> categories) {
        return categories.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public List<CategoryBasicDTO> convertCategoriesToBasicDTOs(Page<Category> categories) {
        return categories.stream().map(category -> modelMapper.map(category, CategoryBasicDTO.class)).collect(Collectors.toList());
    }

    public List<CategoryBasicDTO> convertCategoriesToBasicDTOs(List<Category> categories) {
        return categories.stream().map(category -> modelMapper.map(category, CategoryBasicDTO.class)).collect(Collectors.toList());
    }
}
