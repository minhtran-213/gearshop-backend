package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.payload.request.AddressRequestDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateUserRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.AddressNotFoundException;

import javax.validation.Valid;
import java.util.Optional;

public interface UserService {
    JwtResponse login(LoginRequest loginRequest) throws Exception;
    MessageResponse signup (@Valid SignupRequest signupRequest);
    ResponseDTO getAllUser (Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> firstName);
    ResponseDTO updateProfile (@Valid UpdateUserRequest updateUserRequest);
    ResponseDTO changePassword(String oldPassword, String newPassword);
    ResponseDTO getAddressFromUser(int id);
    ResponseDTO addNewAddress(AddressRequestDTO addressRequestDTO);
    ResponseDTO updateAddress(int id, AddressRequestDTO addressRequestDTO) throws AddressNotFoundException;
}
