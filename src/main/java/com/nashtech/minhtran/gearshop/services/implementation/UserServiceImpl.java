package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.response.UserJwt;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.request.SignupRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.MessageResponse;
import com.nashtech.minhtran.gearshop.exception.EmailExistException;
import com.nashtech.minhtran.gearshop.exception.InvalidEmailException;
import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.RoleRepository;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.security.jwt.JwtUtils;
import com.nashtech.minhtran.gearshop.security.services.UserDetailsImpl;
import com.nashtech.minhtran.gearshop.services.UserService;
import com.nashtech.minhtran.gearshop.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public JwtResponse login(@Valid LoginRequest loginRequest) {
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
    public MessageResponse signup(@Valid SignupRequest signupRequest) {
        if(!Validation.checkValidEmail(signupRequest.getEmail())){
            throw new InvalidEmailException("Email is invalid");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())){
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
    public Page<UserDTO> getAllUser(Optional<Integer> page,
                                    Optional<Integer> size,
                                    Optional<String> sort,
                                    Optional<String> direction,
                                    Optional<String> firstName) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction.isPresent()){
            if (direction.get().equalsIgnoreCase("desc")){
                sortDirection = Sort.Direction.DESC;
            }
        }

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), sortDirection, sort.orElse("id"));
        Page<User> users;
        Page<UserDTO> result;
        if (firstName.isPresent()){
            users = userRepository.findByFirstName(firstName.get(), pageable);
            List<UserDTO> userList = users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
            result = new PageImpl<>(userList, pageable, userList.size());
        } else {
            users = userRepository.findAll(pageable);
            List<UserDTO> userList = users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
            result = new PageImpl<>(userList, pageable, userList.size());
        }
        return result;
    }
}
