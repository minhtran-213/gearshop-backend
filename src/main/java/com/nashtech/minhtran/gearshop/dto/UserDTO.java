package com.nashtech.minhtran.gearshop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserDTO {
    private Integer id;

    @NotBlank
    private String email;

    private String phoneNumber;

    private String address;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Date birthday;

    private String gender;

    @NotNull
    private Date dateCreated;

}
