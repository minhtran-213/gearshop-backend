package com.nashtech.minhtran.gearshop.services;

import com.nashtech.minhtran.gearshop.dto.payload.request.AddressRequestDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateUserRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.*;
import org.springframework.security.access.AccessDeniedException;

import javax.validation.Valid;
import java.util.Optional;

public interface UserService {
    ResponseDTO login(LoginRequest loginRequest);
    ResponseDTO signup (@Valid SignupRequest signupRequest) throws EmailExistException, InvalidEmailException, InvalidPasswordException, SaveUserException;
    ResponseDTO getAllUser (Optional<Integer> page, Optional<Integer> size, Optional<String> sort, Optional<String> direction, Optional<String> firstName) throws RetrieveUserException, ConvertDTOException;
    ResponseDTO updateProfile (@Valid UpdateUserRequest updateUserRequest) throws AccessDeniedException;
    ResponseDTO changePassword(String oldPassword, String newPassword) throws InvalidPasswordException, SaveUserException, AccessDeniedException;
    ResponseDTO getAddressFromUser(int id) throws RetrieveAddressException;
    ResponseDTO addNewAddress(AddressRequestDTO addressRequestDTO) throws RetrieveAddressException;
    ResponseDTO updateAddress(int id, AddressRequestDTO addressRequestDTO) throws AddressNotFoundException;
}
