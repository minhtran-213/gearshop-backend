package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.model.User;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.Optional;

public interface UserService {
    JwtResponse login(LoginRequest loginRequest);
    MessageResponse signup (@Valid SignupRequest signupRequest);
    Page<UserDTO> getAllUser (Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> firstName);
}
