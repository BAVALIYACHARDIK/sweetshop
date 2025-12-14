package com.sweetshop.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String id;
    private String name;
    private String email;
    private String password;
    private String token;
    private String message;

    public AuthResponse(String message) {
        this.message = message;
    }
}
