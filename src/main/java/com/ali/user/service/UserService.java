package com.ali.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ali.user.dto.RegisterRequest;
import com.ali.user.entity.User;
import com.ali.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);
    }
}
