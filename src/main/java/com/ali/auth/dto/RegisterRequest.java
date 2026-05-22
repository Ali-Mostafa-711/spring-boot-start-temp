package com.ali.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import com.ali.user.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
  
    private String username;
    private String email;
    private String password;
    
    @Builder.Default
    private Role role = Role.USER;
}
