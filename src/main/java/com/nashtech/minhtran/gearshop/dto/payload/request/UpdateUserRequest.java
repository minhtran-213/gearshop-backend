package com.nashtech.minhtran.gearshop.dto.payload.request;

import com.nashtech.minhtran.gearshop.model.Address;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String phoneNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Date birthday;

    private String gender;

}
