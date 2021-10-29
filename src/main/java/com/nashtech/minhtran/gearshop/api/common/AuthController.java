package com.nashtech.minhtran.gearshop.api.common;

import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.EmailExistException;
import com.nashtech.minhtran.gearshop.exception.InvalidEmailException;
import com.nashtech.minhtran.gearshop.exception.InvalidPasswordException;
import com.nashtech.minhtran.gearshop.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = userService.login(loginRequest);
        if (jwtResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can't find user");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signupRequest){
        try {
            MessageResponse messageResponse = userService.signup(signupRequest);
            return ResponseEntity.ok().body(messageResponse);
        } catch (InvalidEmailException emailException){
            logger.error(emailException.getMessage());
            throw new InvalidEmailException();
        } catch (EmailExistException emailExistException){
            logger.error(emailExistException.getMessage());
            throw new EmailExistException();
        } catch (InvalidPasswordException passwordException){
            logger.error(passwordException.getMessage());
            throw new InvalidPasswordException();
        }
    }
}
