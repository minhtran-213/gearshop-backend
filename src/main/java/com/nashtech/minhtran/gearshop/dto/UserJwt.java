package com.nashtech.minhtran.gearshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserJwt {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
}
