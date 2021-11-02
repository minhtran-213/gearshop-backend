package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.ManufacturerDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.Optional;

public interface ManufacturerService {
    ResponseDTO getAllManufacturerPaging (Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    ResponseDTO addNewManufacturer (@Valid ManufacturerDTO manufacturerDTO);
    ResponseDTO updateManufacturer (int id, @Valid ManufacturerDTO manufacturerDTO);
    ResponseDTO deleteManufacturer (int id);
}
