package com.ali.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ali.user.dto.AuthResponse;
import com.ali.user.dto.LoginRequest;
import com.ali.user.dto.RegisterRequest;
import com.ali.user.dto.UserResponse;
import com.ali.user.entity.User;
import com.ali.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    

    public AuthResponse Register (RegisterRequest request){
     
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : com.ali.user.enums.Role.USER);
        userRepository.save(user);
        return new AuthResponse();
    }
    


    public AuthResponse Login (LoginRequest request) throws Exception{
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new Exception("User not found"));
        var jwt = jwtService.generateToken(user.getEmail());
        UserResponse userResponse = UserResponse.builder().email(user.getEmail()).username(user.getUsername()).role(user.getRole()).id(user.getId()).build();
        return AuthResponse.builder()
            .token(jwt)
            .user(userResponse)
            .build();

    }
    


    
}
