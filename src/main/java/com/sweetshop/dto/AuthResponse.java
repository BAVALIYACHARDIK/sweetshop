package com.sweetshop.dto;

import lombok.*;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String id;
    private String name;
    private String email;
    private String token;
    private String password;
}
