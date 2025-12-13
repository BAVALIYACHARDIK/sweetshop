package com.sweetshop.service;

import com.sweetshop.dto.AuthResponse;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.model.User;
import com.sweetshop.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@service
public class AuthService {
    private final UserRepository userRepository;
 
    private String jwtSecret=jwt.secret;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse("Email already in use");
        }

        User user = new User(request.getName(),request.getEmail(),request.getPassword());
        User savedUser = userRepository.save(user);
        String token = generateToken(savedUser.getEmail());

        return new AuthResponse(
            savedUser.getId(), 
            savedUser.getName(), 
            savedUser.getEmail(), 
            savedUser.getPassword(),
            token,
            "registration successful"
        );
    }
            
    private String generateToken(User user) {
        
        return Jwts.builder()
                .Subject(user.getId())
                .IssuedAt(new Date())
                .Expiration(new Date(now.getTime() + 86400000))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }
}
