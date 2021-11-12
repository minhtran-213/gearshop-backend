package com.nashtech.minhtran.gearshop.dto.payload.request;

import com.nashtech.minhtran.gearshop.model.ERole;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String phoneNumber;

    private String avatarUrl;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Date birthday;

    private String gender;

    @NotNull
    private ERole role;
}
