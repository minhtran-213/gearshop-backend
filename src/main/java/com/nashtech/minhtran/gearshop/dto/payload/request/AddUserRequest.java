package com.nashtech.minhtran.gearshop.dto.payload.request;

import com.nashtech.minhtran.gearshop.model.ERole;
import com.nashtech.minhtran.gearshop.model.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddUserRequest {

    @NotNull
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private String gender;

    @NotNull
    private Date dateCreated;

    @NotNull
    private ERole role;
}
