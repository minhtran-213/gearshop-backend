package com.nashtech.minhtran.gearshop.dto.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse {
    private String message;
    private int statusCode;
    public MessageResponse(String message, int statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }
}
