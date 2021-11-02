package com.nashtech.minhtran.gearshop.dto.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private Date time;
    private String errorCode;
    private String successCode;
    private Object object;

    public ResponseDTO(Date time, String errorCode) {
        this.time = time;
        this.errorCode = errorCode;
    }

    public ResponseDTO(String successCode, Object object) {
        this.time = new Date();
        this.successCode = successCode;
        this.object = object;
    }
}
