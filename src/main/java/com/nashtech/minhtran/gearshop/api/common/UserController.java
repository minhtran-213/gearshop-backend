package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.dto.payload.request.AddressRequestDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateProfile;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.InvalidOldPasswordException;
import com.nashtech.minhtran.gearshop.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/common")
@SecurityRequirement(name = "minhtran")
@CrossOrigin(origins = "*", maxAge = 30)
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping("/user/password")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.changePassword(oldPassword, newPassword);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (InvalidOldPasswordException e){
            logger.error(e.getMessage());
            throw new InvalidOldPasswordException(e.getMessage());
        } catch (AccessDeniedException e){
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        return response;
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> updateProfile(@Valid @RequestBody UpdateProfile updateProfile){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.updateProfile(updateProfile);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (AccessDeniedException e){
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        return response;
    }

    @PostMapping("/address")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> addNewAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.addNewAddress(addressRequestDTO);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @PutMapping("/address/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> updateAddress(@PathVariable int id, @Valid @RequestBody AddressRequestDTO addressRequestDTO){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.updateAddress(id, addressRequestDTO);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (AccessDeniedException e){
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        return response;
    }
}
