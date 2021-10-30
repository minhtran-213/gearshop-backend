package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.ManufacturerDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ManufacturerService {
    Page<Manufacturer> getAllManufacturer(Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> name);
    MessageResponse addNewManufacturer (ManufacturerDTO manufacturerDTO);
    MessageResponse updateManufacturer (int id, ManufacturerDTO manufacturerDTO);
    MessageResponse deleteManufacturer (int id);
}
