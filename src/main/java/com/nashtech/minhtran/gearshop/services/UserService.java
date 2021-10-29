package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;

import javax.validation.Valid;

public interface UserService {
    JwtResponse login(LoginRequest loginRequest);
    MessageResponse signup (@Valid SignupRequest signupRequest);
}
