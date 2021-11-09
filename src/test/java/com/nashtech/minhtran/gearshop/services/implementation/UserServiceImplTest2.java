package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.model.Address;
import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.AddressRepository;
import com.nashtech.minhtran.gearshop.repo.RoleRepository;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.security.jwt.JwtUtils;
import com.nashtech.minhtran.gearshop.services.UserService;
import com.nashtech.minhtran.gearshop.util.converter.AddressConverter;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest2 {

    @Autowired
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JwtUtils jwtUtils;



    @MockBean
    AddressRepository addressRepository;

    @MockBean
    AddressConverter addressConverter;

    User user;

    Set<Role> roles;

    Role userRole;

    @BeforeEach
    public void setup(){
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build());
        users.add(User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("hoang")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build());
        users.add(User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("hoang")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build());
        Page<User> initMockUsers = new PageImpl<>(users);
        Mockito.when(userRepository.findByFirstName(Mockito.anyString(), Mockito.any())).thenReturn(initMockUsers);
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
    void getAllUser_findAll_expectedSize3() {
        ResponseDTO responseDTO = userService.getAllUser(Optional.of(0), Optional.of(10), Optional.of("id"), Optional.of("asc"), Optional.of("Tran"));
        assertNotNull(responseDTO);
        Page<UserDTO> result = new PageImpl<UserDTO>(new ArrayList<>());
        result = (Page<UserDTO>) responseDTO.getObject();
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
    }

    @Test
    void updateProfile() {
    }

    @Test
    void changePassword_correctOldPassword() {
        user = User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build();
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        ResponseDTO responseDTO = userService.changePassword("Test12345", "Test123456");
        assertNotNull(responseDTO);
        assertThat(responseDTO.getObject()).isEqualTo(true);
    }

    @Test
    void getAddressFromUser() {
        user = User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build();

    }

    @Test
    void addNewAddress() {
    }

    @Test
    void updateAddress() {
    }
}