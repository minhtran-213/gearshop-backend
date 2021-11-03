package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.constants.SuccessCode;
import com.nashtech.minhtran.gearshop.dto.AddressDTO;
import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.AddressRequestDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateUserRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.UserJwt;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.*;
import com.nashtech.minhtran.gearshop.model.Address;
import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.AddressRepository;
import com.nashtech.minhtran.gearshop.repo.RoleRepository;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.security.jwt.JwtUtils;
import com.nashtech.minhtran.gearshop.security.services.UserDetailsImpl;
import com.nashtech.minhtran.gearshop.services.UserService;
import com.nashtech.minhtran.gearshop.util.Validation;
import com.nashtech.minhtran.gearshop.util.converter.AddressConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ModelMapper mapper;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressConverter addressConverter;

    @Override
    public JwtResponse login(@Valid LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (user != null) {
            UserJwt userJwt = UserJwt.builder()
                    .email(user.getEmail())
                    .first_name(user.getFirstName())
                    .last_name(user.getLastName())
                    .id(user.getId())
                    .build();
            return new JwtResponse(jwt, userJwt, roles);
        }
        return null;
    }

    @Override
    public MessageResponse signup(@Valid SignupRequest signupRequest) throws InvalidPasswordException, InvalidEmailException, EmailExistException {
        if (!Validation.checkValidEmail(signupRequest.getEmail())) {
            throw new InvalidEmailException("Email is invalid");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailExistException("Email has taken");
        }
        String newPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(newPassword)
                .firstName(signupRequest.getFirst_name())
                .lastName(signupRequest.getLast_name())
                .dateCreated(new Date())
                .gender(signupRequest.getGender())
                .build();

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("No role found"));
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);
        return new MessageResponse("User registered successful", HttpStatus.OK.value());
    }

    @Override
    public ResponseDTO getAllUser(Optional<Integer> page,
                                  Optional<Integer> size,
                                  Optional<String> sort,
                                  Optional<String> direction,
                                  Optional<String> firstName) throws RetrieveUserException {
        ResponseDTO responseDTO = new ResponseDTO();
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<User> users;
        Page<UserDTO> result;
        if (firstName.isPresent()) {
            try {
                users = userRepository.findByFirstName(firstName.get(), pageable);
                List<UserDTO> userList = users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
                result = new PageImpl<>(userList, pageable, userList.size());
            } catch (Exception e) {
                throw new RetrieveUserException(ErrorCode.ERROR_RETRIEVE_USERS_ERROR);
            }
        } else {
            try {
                users = userRepository.findAll(pageable);
                List<UserDTO> userList = users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
                result = new PageImpl<>(userList, pageable, userList.size());
            } catch (Exception e) {
                throw new RetrieveUserException(ErrorCode.ERROR_RETRIEVE_USERS_ERROR);
            }
        }

        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_USERS_SUCCESS);
        responseDTO.setTime(new Date());
        responseDTO.setObject(result);
        return responseDTO;
    }

    @Override
    public ResponseDTO updateProfile(@Valid UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if(user != null){
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            user.setBirthday(updateUserRequest.getBirthday());
            user.setGender(updateUserRequest.getGender());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User is not logged in or not existed");
        }
        return new ResponseDTO(SuccessCode.UPDATE_PROFILE_SUCCESS, true);
    }


    @Override
    public ResponseDTO changePassword(String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user != null){
            if (!checkIfValidOldPassword(user, oldPassword)){
                throw new InvalidOldPasswordException(ErrorCode.INVALID_OLD_PASSWORD);
            }
            if(Validation.checkValidPassword(newPassword)){
                String hashPass = passwordEncoder.encode(newPassword);
                user.setPassword(hashPass);
                userRepository.save(user);
            } else {
                throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_FORMAT);
            }
        } else {
            throw new RuntimeException("User is not logged in or not existed");
        }
        return new ResponseDTO(SuccessCode.CHANGE_PASSWORD_SUCCESS, true);
    }

    @Override
    public ResponseDTO getAddressFromUser(int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
        List<AddressDTO> result = new ArrayList<>();
        try {
            List<Address> addresses = addressRepository.findByUser(user);
            result = addressConverter.convertToDTOs(addresses);
        } catch (Exception e){
            throw new RetrieveAddressException(ErrorCode.RETRIEVE_ADDRESSES_ERROR);
        }
        return new ResponseDTO(SuccessCode.SUCCESS_RETRIEVE_ADDRESS_FROM_USER, result);
    }

    @Override
    public ResponseDTO addNewAddress(AddressRequestDTO addressRequestDTO) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
        Address address = mapper.map(addressRequestDTO, Address.class);
        address.setUser(user);
        addressRepository.save(address);
        return new ResponseDTO(SuccessCode.ADD_ADDRESS_SUCCESS, true);
    }

    @Override
    public ResponseDTO updateAddress(int id, AddressRequestDTO addressRequestDTO) throws AddressNotFoundException {
        Address address = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(ErrorCode.RETRIEVE_ADDRESS_ERROR));
        address.setAddressName(addressRequestDTO.getAddressName());
        address.setIsDefaultAddress(addressRequestDTO.getIsDefaultAddress());
        address.setCity(addressRequestDTO.getCity());
        address.setDistrict(addressRequestDTO.getDistrict());
        address.setWard(addressRequestDTO.getWard());
        addressRepository.save(address);
        return new ResponseDTO(SuccessCode.UPDATE_ADDRESS_SUCCESSFUL, true);
    }

    private boolean checkIfValidOldPassword(User user, String oldPassword) {
        return BCrypt.checkpw(oldPassword, user.getPassword());
    }
}
