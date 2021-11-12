package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.constants.SuccessCode;
import com.nashtech.minhtran.gearshop.dto.AddressDTO;
import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.*;
import com.nashtech.minhtran.gearshop.dto.payload.response.*;
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
import com.nashtech.minhtran.gearshop.util.converter.UserConverter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    UserConverter userConverter;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseDTO login(@Valid LoginRequest loginRequest) throws BadCredentialsException {
        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (user != null) {
                UserJwt userJwt = UserJwt.builder()
                        .email(user.getEmail())
                        .first_name(user.getFirstName())
                        .last_name(user.getLastName())
                        .id(user.getId())
                        .build();
                JwtResponse jwtResponse = new JwtResponse(jwt, userJwt, roles);
                return new ResponseDTO(SuccessCode.LOGIN_SUCCESSFUL, jwtResponse);
            } else {
                // back end exception
                throw new UserNotFoundException("User not found!");
            }
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            throw new BadCredentialsException(ErrorCode.WRONG_USERNAME_OR_PASSWORD);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public ResponseDTO signup(@Valid SignupRequest signupRequest) throws InvalidPasswordException, InvalidEmailException, EmailExistException {
        try {
            if (!Validation.checkValidEmail(signupRequest.getEmail())) {
                throw new InvalidEmailException("Email is invalid");
            }

            if (!Validation.checkValidPassword(signupRequest.getPassword())) {
                throw new InvalidPasswordException("Password is invalid");
            }
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                throw new EmailExistException("Email has taken");
            }
            String newPassword = passwordEncoder.encode(signupRequest.getPassword());
            User user = User.builder()
                    .id(0)
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

            return new ResponseDTO("User registered successful", true);
        } catch (InvalidPasswordException e) {
            logger.error(e.getMessage());
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_FORMAT);
        } catch (InvalidEmailException e) {
            logger.error(e.getMessage());
            throw new InvalidEmailException(ErrorCode.INVALID_EMAIL_FORMAT);
        } catch (EmailExistException e) {
            logger.error(e.getMessage());
            throw new EmailExistException(ErrorCode.EXIST_EMAIL);
        }

    }

    @Override
    public ResponseDTO getAllUser(Optional<Integer> page,
                                  Optional<Integer> size,
                                  Optional<String> sort,
                                  Optional<String> direction,
                                  Optional<String> firstName) throws RetrieveUserException, ConvertDTOException {
        ResponseDTO responseDTO = new ResponseDTO();
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()) {
            if (direction.get().equalsIgnoreCase("desc")) {
                sortDirection = Sort.Direction.DESC;
            }
        }

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<User> users = new PageImpl<>(new ArrayList<>());
        Page<UserDTO> result = new PageImpl<>(new ArrayList<>());
        if (firstName.isPresent()) {
            try {
                users = userRepository.findByFirstName(firstName.get(), pageable);
                try {
                    List<UserDTO> userList = userConverter.convertToListUserDTO(users);
                    result = new PageImpl<>(userList, pageable, userList.size());
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (ConvertDTOException e) {
                logger.error(e.getMessage());
                throw new ConvertDTOException();
            } catch (RetrieveUserException e) {
                logger.error(e.getMessage());
                throw new RetrieveUserException(ErrorCode.ERROR_RETRIEVE_USERS_ERROR);
            }
        } else {
            try {
                users = userRepository.findAll(pageable);
                try {
                    List<UserDTO> userList = userConverter.convertToListUserDTO(users);
                    result = new PageImpl<>(userList, pageable, userList.size());
                } catch (ConvertDTOException e) {
                    logger.error(e.getMessage());
                    throw new ConvertDTOException();
                }
            } catch (ConvertDTOException e) {
                logger.error(e.getMessage());
            } catch (RetrieveUserException e) {
                logger.error(e.getMessage());
                throw new RetrieveUserException(ErrorCode.ERROR_RETRIEVE_USERS_ERROR);
            }
        }

        PageResponse response = PageResponse.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .content(result.getContent())
                .currentPage(users.getNumber()).build();

        responseDTO.setSuccessCode(SuccessCode.RETRIEVE_USERS_SUCCESS);
        responseDTO.setTime(new Date());
        responseDTO.setObject(response);
        return responseDTO;
    }

    @Override
    public ResponseDTO updateProfile(@Valid UpdateProfile updateProfile) throws AccessDeniedException {
        try {
            User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
            if (user != null) {
                user.setPhoneNumber(updateProfile.getPhoneNumber());
                user.setFirstName(updateProfile.getFirstName());
                user.setLastName(updateProfile.getLastName());
                user.setBirthday(updateProfile.getBirthday());
                user.setGender(updateProfile.getGender());
                try {
                    userRepository.save(user);
                } catch (SaveUserException e) {
                    throw new SaveUserException();
                }
            } else {
                throw new AccessDeniedException("User is not logged in or not existed");
            }
            return new ResponseDTO(SuccessCode.UPDATE_PROFILE_SUCCESS, true);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            throw new AccessDeniedException("Unauthorized");
        }
    }


    @Override
    public ResponseDTO changePassword(String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        if (user != null) {
            if (!checkIfValidOldPassword(user, oldPassword)) {
                throw new InvalidOldPasswordException(ErrorCode.INVALID_OLD_PASSWORD);
            }
            if (Validation.checkValidPassword(newPassword)) {
                String hashPass = passwordEncoder.encode(newPassword);
                user.setPassword(hashPass);
                try {
                    userRepository.save(user);
                } catch (SaveUserException e) {
                    logger.error(e.getMessage());
                    throw new SaveUserException();
                }
            } else {
                logger.error(ErrorCode.INVALID_PASSWORD_FORMAT);
                throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_FORMAT);
            }
        } else {
            logger.error("User is not logged in or not existed");
            throw new AccessDeniedException("User is not logged in or not existed");
        }
        return new ResponseDTO(SuccessCode.CHANGE_PASSWORD_SUCCESS, true);
    }

    @Override
    public ResponseDTO getAddressFromUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
        List<AddressDTO> result = new ArrayList<>();
        try {
            List<Address> addresses = addressRepository.findByUser(user);
            result = addressConverter.convertToDTOs(addresses);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RetrieveAddressException(ErrorCode.RETRIEVE_ADDRESSES_ERROR);
        }
        return new ResponseDTO(SuccessCode.SUCCESS_RETRIEVE_ADDRESS_FROM_USER, result);
    }

    @Override
    public ResponseDTO addNewAddress(AddressRequestDTO addressRequestDTO) {
        try {
            User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
            Address address = mapper.map(addressRequestDTO, Address.class);
            address.setUser(user);
            try {
                addressRepository.save(address);
            } catch (SaveAddressException e) {
                logger.error(e.getMessage());
                throw new SaveAddressException(ErrorCode.SAVE_ADDRESS_FAIL);
            }
            return new ResponseDTO(SuccessCode.ADD_ADDRESS_SUCCESS, true);
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            throw new AccessDeniedException(ErrorCode.FORBIDDEN);
        }

    }

    @Override
    public ResponseDTO updateAddress(int id, AddressRequestDTO addressRequestDTO) throws AddressNotFoundException {
        Address address = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(ErrorCode.RETRIEVE_ADDRESS_ERROR));
        address.setAddressName(addressRequestDTO.getAddressName());
        address.setIsDefaultAddress(addressRequestDTO.getIsDefaultAddress());
        address.setCity(addressRequestDTO.getCity());
        address.setDistrict(addressRequestDTO.getDistrict());
        address.setWard(addressRequestDTO.getWard());
        try {
            addressRepository.save(address);
        } catch (SaveAddressException e) {
            logger.error(e.getMessage());
            throw new SaveAddressException(e.getMessage());
        }
        return new ResponseDTO(SuccessCode.UPDATE_ADDRESS_SUCCESSFUL, true);
    }

    @Override
    public ResponseDTO addUser(@Valid AddUserRequest request) throws ConvertDTOException, SaveUserException, InvalidEmailException, InvalidPasswordException, EmailExistException {
        try {
            if (!Validation.checkValidEmail(request.getEmail())){
                throw new InvalidEmailException("Email is invalid");
            }
            if (!Validation.checkValidPassword(request.getPassword())){
                throw new InvalidPasswordException("Password is invalid");
            }
            if (userRepository.existsByEmail(request.getEmail())){
                throw new EmailExistException("Email has taken");
            }

            String newPassword = passwordEncoder.encode(request.getPassword());
            User user = User.builder()
                    .id(0)
                    .email(request.getEmail())
                    .password(newPassword)
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .gender(request.getGender())
                    .birthday(request.getBirthday())
                    .phoneNumber(request.getPhoneNumber())
                    .avatarUrl(request.getAvatarUrl())
                    .dateCreated(new Date())
                    .build();
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(request.getRole()).orElseThrow(() -> new RuntimeException("No role found"));
            roles.add(role);
            user.setRoles(roles);
            try {
                userRepository.save(user);
            } catch (SaveUserException e){
                logger.error(e.getMessage());
                throw new SaveUserException();
            } return new ResponseDTO("User register successful", true);
        } catch (InvalidPasswordException e){
            logger.error(e.getMessage());
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_FORMAT);
        } catch (InvalidEmailException e) {
            logger.error(e.getMessage());
            throw new InvalidEmailException(ErrorCode.INVALID_EMAIL_FORMAT);
        } catch (EmailExistException e) {
            logger.error(e.getMessage());
            throw new EmailExistException(ErrorCode.EXIST_EMAIL);
        }
    }

    @Override
    public ResponseDTO updateUser(int id, UserUpdateRequest request) throws SaveUserException{
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
        try {
            user.setGender(request.getGender());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setBirthday(request.getBirthday());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setAvatarUrl(request.getAvatarUrl());
            for (Role role : user.getRoles()){
                if (role.getName().equals(request.getRole())){
                    throw new RoleExistedException("Role has existed");
                }
            }
            Role role = roleRepository.findByName(request.getRole()).orElseThrow(() -> new RuntimeException("No role found"));
            user.getRoles().add(role);
            try {
                userRepository.save(user);
            } catch (Exception e){
                logger.error(e.getMessage());
                throw new SaveUserException(ErrorCode.SAVE_USER_FAIL);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new SaveUserException(ErrorCode.SAVE_USER_FAIL);
        }
        return new ResponseDTO("User update successful", true);
    }

    @Override
    public ResponseDTO deleteUser(int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
        try {
            userRepository.delete(user);
        } catch (UserNotFoundException e){
            logger.error(e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
        return new ResponseDTO("Delete successful", true);
    }

    private boolean checkIfValidOldPassword(User user, String oldPassword) {
        return BCrypt.checkpw(oldPassword, user.getPassword());
    }
}
