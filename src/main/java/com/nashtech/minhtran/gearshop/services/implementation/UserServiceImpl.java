package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.UserJwt;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
}
