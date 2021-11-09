package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.repo.UserRepository;
import com.nashtech.minhtran.gearshop.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerAdminTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getUsers() {
        List<User> users = Mockito.mock(ArrayList.class);
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    void getAddressesFromUser() {
    }
}