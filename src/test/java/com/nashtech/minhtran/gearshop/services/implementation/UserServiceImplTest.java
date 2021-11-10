package com.nashtech.minhtran.gearshop.services.implementation;

import com.nashtech.minhtran.gearshop.constants.ErrorCode;
import com.nashtech.minhtran.gearshop.dto.payload.request.LoginRequest;
import com.nashtech.minhtran.gearshop.dto.payload.response.JwtResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.PageResponse;
import com.nashtech.minhtran.gearshop.dto.payload.response.ResponseDTO;
import com.nashtech.minhtran.gearshop.exception.RetrieveUserException;
import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.AddressRepository;
import com.nashtech.minhtran.gearshop.repo.RoleRepository;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.security.jwt.JwtUtils;
import com.nashtech.minhtran.gearshop.security.services.UserDetailsImpl;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.swing.text.html.Option;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
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

    @Test
    void login_failed() throws Exception {
        user = User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build();
        roles = new HashSet<>();


        userRole = new Role(-1, ERole.ROLE_USER);
        roles.add(userRole);

        user.setRoles(roles);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(new BadCredentialsException("Bad credentials"));
        UserDetailsImpl userDetailImpl = UserDetailsImpl.build(user);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetailImpl);
        Mockito.when(userRepository.findById(-1)).thenReturn(Optional.of(user));
        Exception exception = assertThrows(AuthenticationException.class, () -> userService.login(new LoginRequest("test@test.com", "Test12345")));
        assertEquals(exception.getMessage(), "Bad credentials");
        assertEquals(exception.getClass(), BadCredentialsException.class);
    }

    @Test
    void login_success() throws Exception {
        user = User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build();
        roles = new HashSet<>();


        userRole = new Role(-1, ERole.ROLE_USER);
        roles.add(userRole);

        user.setRoles(roles);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        UserDetailsImpl userDetailImpl = UserDetailsImpl.build(user);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetailImpl);
        Mockito.when(userRepository.findById(-1)).thenReturn(Optional.of(user));
        JwtResponse jwtResponse = userService.login(new LoginRequest("test@test.com", "Test12345"));
        assertNotNull(jwtResponse);
        assertNotNull(jwtResponse.getToken());
    }

    @Test
    void login_failed_returnNull() throws Exception {
        user = User.builder()
                .id(-1)
                .email("test@test.com")
                .lastName("minh")
                .firstName("tran")
                .password(passwordEncoder.encode("Test12345"))
                .dateCreated(new Date())
                .build();
        roles = new HashSet<>();


        userRole = new Role(-1, ERole.ROLE_USER);
        roles.add(userRole);

        user.setRoles(roles);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        UserDetailsImpl userDetailImpl = UserDetailsImpl.build(user);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetailImpl);
        Mockito.when(userRepository.findById(-1)).thenReturn(Optional.empty());
        JwtResponse jwtResponse = userService.login(new LoginRequest("test@test.com", "Test12345"));
        assertNull(jwtResponse);
    }

    @Test
    void signup() {
    }

    @Test
    void getAllUser_findAll_expectedSize3() {
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
        Mockito.when(userRepository.findAll(Mockito.any(Pageable.class))).thenReturn(initMockUsers);
        ResponseDTO responseDTO = userService.getAllUser(Optional.of(0), Optional.of(10), Optional.of("id"), Optional.of("asc"), Optional.empty());
        assertNotNull(responseDTO);
        PageResponse result = (PageResponse) responseDTO.getObject();
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
    }

    @Test
    void getAllUser_findByFirstname_expectedSize3() {
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
        ResponseDTO responseDTO = userService.getAllUser(Optional.of(0), Optional.of(10), Optional.of("id"), Optional.of("asc"), Optional.of("Tran"));
        assertNotNull(responseDTO);
        PageResponse result = (PageResponse) responseDTO.getObject();
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
    }

    @Test
    void getAllUser_findByFirstname_expectedThrowException() {
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
        Mockito.when(userRepository.findByFirstName(Mockito.anyString(), Mockito.any())).thenThrow(new RetrieveUserException(ErrorCode.ERROR_RETRIEVE_USERS_ERROR));
        Exception exception = assertThrows(RetrieveUserException.class, () -> userService.getAllUser(Optional.of(0), Optional.of(10), Optional.of("id"), Optional.of("asc"), Optional.of("Tran")));
        assertEquals(exception.getMessage(), ErrorCode.ERROR_RETRIEVE_USERS_ERROR);
        assertEquals(exception.getClass(), RetrieveUserException.class);
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