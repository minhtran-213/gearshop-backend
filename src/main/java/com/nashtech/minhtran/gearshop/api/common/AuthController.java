package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.*;
import com.nashtech.minhtran.gearshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 30)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @Operation(summary = "login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))}),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content)
    })
    public ResponseEntity<ResponseDTO> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO responseDTO = userService.login(loginRequest);
            response = ResponseEntity.ok().body(responseDTO);
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
           throw new BadCredentialsException(e.getMessage());
        }
        return response;
    }

    @PostMapping("/signup")
    @Operation(summary = "signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "signup successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "bad request" ,content = @Content)
    })
    public ResponseEntity<ResponseDTO> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        ResponseEntity<ResponseDTO> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            ResponseDTO messageResponse = userService.signup(signupRequest);
            return ResponseEntity.ok().body(messageResponse);
        } catch (InvalidEmailException emailException) {
            logger.error(emailException.getMessage());
            throw new InvalidEmailException(emailException.getMessage());
        } catch (EmailExistException emailExistException) {
            logger.error(emailExistException.getMessage());
            throw new EmailExistException(emailExistException.getMessage());
        } catch (InvalidPasswordException passwordException) {
            logger.error(passwordException.getMessage());
            throw new InvalidPasswordException(passwordException.getMessage());
        }
    }
}
