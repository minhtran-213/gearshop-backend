package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.payload.response.PageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.model.Category;
import com.nashtech.minhtran.gearshop.repo.CategoryRepository;
import com.nashtech.minhtran.gearshop.services.CategoryService;
import com.nashtech.minhtran.gearshop.util.converter.CategoryConverter;
import org.apache.coyote.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ModelMapper modelMapper;



    @Test
    void getAllCategoryPaging_findAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().id(1).name("hello").build());
        categories.add(Category.builder().id(2).name("hello000").build());
        categories.add(Category.builder().id(3).name("helloooo").build());

        Page<Category> initMock = new PageImpl<>(categories);
        Mockito.when(categoryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(initMock);
        ResponseDTO responseDTO = categoryService.getAllCategoryPaging(Optional.of(0), Optional.of(10), Optional.of("id"), Optional.of("asc"), Optional.empty());
        Assertions.assertThat(responseDTO).isNotNull();
        PageResponse result = (PageResponse) responseDTO.getObject();
        Assertions.assertThat(result.getContent().size()).isEqualTo(3);
    }

    @Test
    void getSubCategoriesByParentCategories() {
    }

    @Test
    void addNewCategory() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getAllParentCategory() {
    }

    @Test
    void getAllCategoriesForAdmin() {
    }
}