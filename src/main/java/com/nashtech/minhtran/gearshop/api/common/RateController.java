package com.nashtech.minhtran.gearshop.api.common;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@SecurityRequirement(name = "minhtran")
public class RateController {
}
