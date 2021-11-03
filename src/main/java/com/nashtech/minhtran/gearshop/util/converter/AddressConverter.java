package com.nashtech.minhtran.gearshop.util.converter;

import com.nashtech.minhtran.gearshop.dto.AddressDTO;
import com.nashtech.minhtran.gearshop.dto.payload.request.AddressRequestDTO;
import com.nashtech.minhtran.gearshop.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressConverter {

    @Autowired
    ModelMapper modelMapper;


    public List<AddressDTO> convertToDTOs (List<Address> addresses){
        return addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class)).collect(Collectors.toList());
    }
}

