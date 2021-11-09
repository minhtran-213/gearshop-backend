package com.nashtech.minhtran.gearshop.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.RoleRepository;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        if (user == null) {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("No role found"));
            roles.add(userRole);
            user = User.builder()
                    .email("test@test.com")
                    .password(passwordEncoder.encode("Test12345"))
                    .firstName("Minh")
                    .lastName("Tran")
                    .roles(roles)
                    .build();
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode("Test12345"));
            userRepository.save(user);
        }
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void authenticateUser_whenWrongPassword_thenReturn401() {
        try {
            mockMvc.perform(post("/api/v1/auth/login")
                    .content(asJsonString(User.builder().email("test@test.com").password("Test1234").build()))).andExpect(status().isUnauthorized());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void signUp() {
    }
}