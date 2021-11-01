package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.CategoryDTO;
import com.nashtech.minhtran.gearshop.dto.ManufacturerDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.CategoryRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.model.Category;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.Optional;

public interface CategoryService {
    ResponseDTO getAllCategory(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    ResponseDTO addNewCategory (@Valid CategoryRequest categoryRequest);
    ResponseDTO updateCategory (int id, @Valid CategoryRequest categoryRequest);
    ResponseDTO deleteCategory (int id);
}
