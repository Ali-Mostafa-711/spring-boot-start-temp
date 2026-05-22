package com.ali.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ali.user.dto.AuthResponse;
import com.ali.user.dto.LoginRequest;
import com.ali.user.dto.RegisterRequest;
import com.ali.user.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    
    @PostMapping("/register")
    public AuthResponse Register(@RequestBody RegisterRequest user) {
        
        return authService.Register(user);
    }
    

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) throws Exception {
        return authService.Login(request);
    }
    
}
