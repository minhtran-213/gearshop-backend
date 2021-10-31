package com.nashtech.minhtran.gearshop.dto.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private UserJwt userJwt;
    private List<String> roles;

    public JwtResponse(String token, UserJwt userJwt, List<String> roles) {
        this.token = token;
        this.userJwt = userJwt;
        this.roles = roles;
    }
}
