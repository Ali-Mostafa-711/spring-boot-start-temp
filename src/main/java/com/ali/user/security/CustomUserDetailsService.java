package com.ali.user.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ali.user.entity.CustomUserDetails;
import com.ali.user.entity.User;
import com.ali.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService  {

    private final UserRepository userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user =  userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"));
    
      return new CustomUserDetails(user);
    }

}
