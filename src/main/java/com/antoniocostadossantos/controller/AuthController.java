package com.antoniocostadossantos.controller;

import com.antoniocostadossantos.model.UserEntity;
import com.antoniocostadossantos.model.UserLoginDTO;
import com.antoniocostadossantos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserLoginDTO userLogin){
        UserEntity userEntity = userService.register(userLogin.username(), userLogin.password());
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLogin){
        String userToken = userService.loginUser(userLogin);

        return ResponseEntity.ok(Map.of("token", userToken));
    }
}
