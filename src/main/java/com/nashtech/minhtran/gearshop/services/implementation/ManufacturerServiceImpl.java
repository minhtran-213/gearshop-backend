package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.ManufacturerDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.EmptyBodyException;
import com.nashtech.minhtran.gearshop.exception.EmptyNameManufacturerException;
import com.nashtech.minhtran.gearshop.exception.ManufacturerNotExistException;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import com.nashtech.minhtran.gearshop.repo.ManufacturerRepository;
import com.nashtech.minhtran.gearshop.services.ManufacturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public Page<Manufacturer> getAllManufacturer(Optional<Integer> page,
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
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<Manufacturer> manufacturers;
        if (name.isPresent()){
            manufacturers = manufacturerRepository.findByName(name.get(), pageable);
        } else {
            manufacturers = manufacturerRepository.findAll(pageable);
        }

        return manufacturers;
    }

    @Override
    public MessageResponse addNewManufacturer(@Valid ManufacturerDTO manufacturerDTO) {
        if (manufacturerDTO == null){
            throw new EmptyBodyException("Body cannot be null");
        } else if (manufacturerDTO.getName().isEmpty()){
            throw new EmptyNameManufacturerException("Name cannot be empty");
        } else {
            Manufacturer manufacturer = mapper.map(manufacturerDTO, Manufacturer.class);
            manufacturer.setId(0);
            manufacturerRepository.save(manufacturer);
        }
        return new MessageResponse("Add new manufacturer successful", HttpStatus.OK.value());
    }

    @Override
    public MessageResponse updateManufacturer(int id, @Valid ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if (manufacturer == null){
            throw new ManufacturerNotExistException("Manufacturer not exist");
        } else if (manufacturerDTO == null){
            throw new EmptyBodyException("Body cannot be null");
        }
        else if (manufacturerDTO.getName().isEmpty()){
            throw new EmptyNameManufacturerException("Name cannot be empty");
        } else {
            manufacturer.setName(manufacturerDTO.getName());
            manufacturerRepository.save(manufacturer);
            return new MessageResponse("update successful", HttpStatus.OK.value());
        }
    }

    @Override
    public MessageResponse deleteManufacturer(int id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if (manufacturer == null){
            throw new ManufacturerNotExistException("Manufacturer not exist");
        }
        manufacturerRepository.delete(manufacturer);
        return new MessageResponse("delete successful", HttpStatus.OK.value());
    }
}
