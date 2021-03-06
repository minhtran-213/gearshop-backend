package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.AddUserRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UserUpdateRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.*;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "minhtran")
@CrossOrigin(origins = "*", maxAge = 30)
public class UserControllerAdmin {
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserControllerAdmin.class);

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getUsers(@RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size,
                                                @RequestParam Optional<String> sort,
                                                @RequestParam Optional<String> direction,
                                                @RequestParam Optional<String> firstName) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO result = userService.getAllUser(page, size, sort, direction, firstName);
            response = ResponseEntity.ok().body(result);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        return response;
    }

    @GetMapping("/users/{id}/address")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAddressesFromUser(@PathVariable int id) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.getAddressFromUser(id);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
        return response;
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.addUser(addUserRequest);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        } catch (SaveUserException e) {
            logger.error(e.getMessage());
            throw new SaveUserException(e.getMessage());
        } catch (InvalidEmailException e) {
            logger.error(e.getMessage());
            throw new InvalidEmailException(e.getMessage());
        } catch (InvalidPasswordException e) {
            logger.error(e.getMessage());
            throw new InvalidPasswordException(e.getMessage());
        } catch (EmailExistException e){
            logger.error(e.getMessage());
            throw new EmailExistException(e.getMessage());
        } catch (Exception e){
            logger.info(addUserRequest.toString());
            logger.error(e.getMessage());
        }
        return response;
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateUser (@PathVariable int id, @RequestBody UserUpdateRequest request){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.updateUser(id, request);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (AccessDeniedException e){
            logger.error(e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        } catch (SaveUserException e){
            logger.error(e.getMessage());
            throw new SaveUserException(e.getMessage());
        } catch (UserNotFoundException e){
          logger.error(e.getMessage());
          throw new UserNotFoundException(e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteUSer(@PathVariable int id){
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.deleteUser(id);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (UserNotFoundException e){
            logger.error(e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        return response;
    }
}
