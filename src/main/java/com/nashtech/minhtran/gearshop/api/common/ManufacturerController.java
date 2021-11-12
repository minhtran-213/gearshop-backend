package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.ManufacturerNotExistException;
import com.nashtech.minhtran.gearshop.exception.RetrieveManufacturerException;
import com.nashtech.minhtran.gearshop.services.ManufacturerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/common")
@SecurityRequirement(name = "minhtran")
@CrossOrigin(origins = "*", maxAge = 30)
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @GetMapping("/manufacturers")
    public ResponseEntity<ResponseDTO> getAllManufacturer(){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = manufacturerService.getAllManufacturers();
            response = ResponseEntity.ok().body(result);
        } catch (RetrieveManufacturerException e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @GetMapping("/manufacturer/{id}")
    public ResponseEntity<ResponseDTO> getManufacturer(@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = manufacturerService.getManufacturer(id);
            response = ResponseEntity.ok().body(result);
        } catch (ManufacturerNotExistException e){
            logger.error(e.getMessage());
            throw new ManufacturerNotExistException(e.getMessage());
        }
        return response;
    }
}
