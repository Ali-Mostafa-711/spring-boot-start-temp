package com.ali.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ali.auth.dto.AuthResponse;
import com.ali.auth.dto.LoginRequest;
import com.ali.auth.dto.RegisterRequest;
import com.ali.auth.service.AuthService;

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
