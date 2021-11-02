package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.constants.SuccessCode;
import com.nashtech.minhtran.gearshop.dto.ManufacturerDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.EmptyBodyException;
import com.nashtech.minhtran.gearshop.exception.EmptyNameManufacturerException;
import com.nashtech.minhtran.gearshop.exception.ManufacturerNotExistException;
import com.nashtech.minhtran.gearshop.exception.RetrieveManufacturerException;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import com.nashtech.minhtran.gearshop.repo.ManufacturerRepository;
import com.nashtech.minhtran.gearshop.services.ManufacturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ResponseDTO getAllManufacturerPaging(Optional<Integer> page,
                                                Optional<Integer> size,
                                                Optional<String> sort,
                                                Optional<String> direction,
                                                Optional<String> name) throws RetrieveManufacturerException {
        ResponseDTO responseDTO = new ResponseDTO();
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<Manufacturer> manufacturers;
        if (name.isPresent()) {
            try {
                manufacturers = manufacturerRepository.findByName(name.get(), pageable);
            } catch (Exception e) {
                throw new RetrieveManufacturerException(ErrorCode.ERROR_RETRIEVE_MANUFACTURERS);
            }
        } else {
            try {
                manufacturers = manufacturerRepository.findAll(pageable);
            } catch (Exception e) {
                throw new RetrieveManufacturerException(ErrorCode.ERROR_RETRIEVE_MANUFACTURERS);
            }
        }
        responseDTO.setTime(new Date());
        responseDTO.setObject(manufacturers);
        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_MANUFACTURERS_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO addNewManufacturer(@Valid ManufacturerDTO manufacturerDTO) throws EmptyBodyException, EmptyNameManufacturerException{
        ResponseDTO responseDTO = new ResponseDTO();
        if (manufacturerDTO == null) {
            throw new EmptyBodyException(ErrorCode.EMPTY_BODY);
        } else if (manufacturerDTO.getName().isEmpty()) {
            throw new EmptyNameManufacturerException(ErrorCode.ERROR_MANUFACTURER_EMPTY_NAME);
        } else {
            Manufacturer manufacturer = mapper.map(manufacturerDTO, Manufacturer.class);
            manufacturer.setId(0);
            manufacturerRepository.save(manufacturer);
        }
        responseDTO.setTime(new Date());
        responseDTO.setObject(true);
        responseDTO.setSuccessCode(SuccessCode.ADD_MANUFACTURER_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO updateManufacturer(int id, @Valid ManufacturerDTO manufacturerDTO)
            throws ManufacturerNotExistException, EmptyBodyException, EmptyNameManufacturerException {
        ResponseDTO responseDTO = new ResponseDTO();
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if (manufacturer == null) {
            throw new ManufacturerNotExistException(ErrorCode.ERROR_MANUFACTURER_NOT_EXIST);
        } else if (manufacturerDTO == null) {
            throw new EmptyBodyException(ErrorCode.EMPTY_BODY);
        } else if (manufacturerDTO.getName().isEmpty()) {
            throw new EmptyNameManufacturerException(ErrorCode.ERROR_MANUFACTURER_EMPTY_NAME);
        } else {
            manufacturer.setName(manufacturerDTO.getName());
            manufacturerRepository.save(manufacturer);
            responseDTO.setTime(new Date());
            responseDTO.setObject(true);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_MANUFACTURER_SUCCESS);
            return responseDTO;
        }
    }

    @Override
    public ResponseDTO deleteManufacturer(int id) throws ManufacturerNotExistException {
        ResponseDTO responseDTO = new ResponseDTO();
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        if (manufacturer == null) {
            throw new ManufacturerNotExistException(ErrorCode.ERROR_MANUFACTURER_NOT_EXIST);
        }
        manufacturerRepository.delete(manufacturer);
        responseDTO.setTime(new Date());
        responseDTO.setObject(true);
        responseDTO.setSuccessCode(SuccessCode.DELETE_MANUFACTURER_SUCCESS);
        return responseDTO;
    }

    @Override
    public ResponseDTO getAllManufacturers() throws RetrieveManufacturerException {
        List<Manufacturer> manufacturers;
        try {
            manufacturers = manufacturerRepository.findAll(Sort.by("name"));
        } catch (Exception e){
            throw new RetrieveManufacturerException(ErrorCode.ERROR_RETRIEVE_MANUFACTURERS);
        }
        return new  ResponseDTO(SuccessCode.RETRIEVE_MANUFACTURERS_SUCCESS, manufacturers);
    }

    @Override
    public ResponseDTO getManufacturer(int id) throws ManufacturerNotExistException {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() -> new ManufacturerNotExistException(ErrorCode.ERROR_MANUFACTURER_NOT_EXIST));
        return new ResponseDTO(SuccessCode.RETRIEVE_SINGLE_MANUFACTURERS_SUCCESS, manufacturer);
    }


}
