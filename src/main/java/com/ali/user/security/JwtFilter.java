package com.ali.user.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.ali.user.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService; 
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
      

          if (request.getServletPath().startsWith("/auth")) {
                filterChain.doFilter(request, response);
                return;
           }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")  ) {
            filterChain.doFilter(request, response);
            return;
        }
      
        try {
            String token = authHeader.substring(7);
            String email = jwtService.extractUserEmail(token);

            if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                 if (jwtService.isValidToken(token, userDetails)) {

                   UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Invalid/expired/malformed token — continue without authentication
            // Spring Security's AuthorizationFilter will handle the 401/403
        }

        filterChain.doFilter(request, response);
    }
}