package com.nashtech.minhtran.gearshop.util.converter;

import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.UpdateUserRequest;
import com.nashtech.minhtran.gearshop.exception.ConvertDTOException;
import com.nashtech.minhtran.gearshop.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    @Autowired
    ModelMapper mapper;

    public List<UserDTO> convertToListUserDTO (Page<User> users) throws ConvertDTOException {
        return users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
}
