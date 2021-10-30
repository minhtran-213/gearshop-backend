package com.nashtech.minhtran.gearshop.api.admin;

import com.nashtech.minhtran.gearshop.dto.UserDTO;
import com.nashtech.minhtran.gearshop.model.User;
import com.nashtech.minhtran.gearshop.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "minhtran")
public class UserControllerAdmin {
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserControllerAdmin.class);

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam Optional<Integer> page,
                                      @RequestParam Optional<Integer> size,
                                      @RequestParam Optional<String> sort,
                                      @RequestParam Optional<String> direction,
                                      @RequestParam Optional<String> firstName) {
       ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            Page<UserDTO> result = userService.getAllUser(page, size, sort, direction, firstName);
            response = ResponseEntity.ok().body(result);
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        return response;
    }
}
