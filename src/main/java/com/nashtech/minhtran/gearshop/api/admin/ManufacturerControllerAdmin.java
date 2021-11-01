package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.dto.ManufacturerDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.EmptyBodyException;
import com.nashtech.minhtran.gearshop.exception.EmptyNameManufacturerException;
import com.nashtech.minhtran.gearshop.exception.ManufacturerNotExistException;
import com.nashtech.minhtran.gearshop.model.Manufacturer;
import com.nashtech.minhtran.gearshop.services.ManufacturerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "minhtran")
public class ManufacturerControllerAdmin {

    @Autowired
    ManufacturerService manufacturerService;

    Logger logger = LoggerFactory.getLogger(ManufacturerControllerAdmin.class);

    @GetMapping("/manufacturers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllManufacturer(@RequestParam Optional<Integer> page,
                                                          @RequestParam Optional<Integer> size,
                                                          @RequestParam Optional<String> sort,
                                                          @RequestParam Optional<String> direction,
                                                          @RequestParam Optional<String> name) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO manufacturers = manufacturerService.getAllManufacturer(page, size, sort, direction, name);
            response = new ResponseEntity<>(manufacturers, HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @PostMapping("/manufacturer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewManufacturer(@RequestBody ManufacturerDTO manufacturerDTO){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = manufacturerService.addNewManufacturer(manufacturerDTO);
            response = new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @PutMapping("/manufacturer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateManufacturer(@PathVariable int id, @RequestBody ManufacturerDTO manufacturerDTO){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = manufacturerService.updateManufacturer(id, manufacturerDTO);
            response = new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/manufacturer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteManufacturer(@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = manufacturerService.deleteManufacturer(id);
            response = new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        return response;
    }
}
