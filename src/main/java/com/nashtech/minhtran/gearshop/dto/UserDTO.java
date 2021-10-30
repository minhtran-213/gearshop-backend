package com.nashtech.minhtran.gearshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
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
