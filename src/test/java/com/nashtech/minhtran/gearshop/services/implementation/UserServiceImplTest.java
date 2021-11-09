package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.AddressRepository;
import com.nashtech.minhtran.gearshop.repo.RoleRepository;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.security.jwt.JwtUtils;
import com.nashtech.minhtran.gearshop.services.UserService;
import com.nashtech.minhtran.gearshop.util.converter.AddressConverter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

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
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressConverter addressConverter;

    User user;

    Set<Role> roles;

    Role userRole;

    @BeforeEach
    public void setup(){

        user = User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build();
        roles = new HashSet<>();

        Optional<Role> userRoleOPT = roleRepository.findByName(ERole.ROLE_USER);
        if(userRoleOPT.isPresent()){
            userRole = userRoleOPT.get();
        } else {
            userRole = roleRepository.save(new Role(-1, ERole.ROLE_USER));
        }
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        Authentication authentication = Mockito.mock(Authentication.class);
//        Mockito.mock(authenticationManager.authenticate(Mockito.any()).thenReturn(authentication));
    }

    @AfterEach
    public void destroy(){
        userRepository.delete(user);
    }


    @Test
    void login() throws Exception {
        JwtResponse response = userService.login(new LoginRequest("test@test.com", "Test12345"));
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals(user.getEmail(), response.getUserJwt().getEmail());
    }

    @Test
    void signup() {
    }

    @Test
    void getAllUser() {

    }

    @Test
    void updateProfile() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void getAddressFromUser() {
    }

    @Test
    void addNewAddress() {
    }

    @Test
    void updateAddress() {
    }
}