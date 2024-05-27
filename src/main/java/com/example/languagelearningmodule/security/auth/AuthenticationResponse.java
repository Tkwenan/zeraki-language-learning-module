package com.example.languagelearningmodule.security.auth;

import lombok.*;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }
}
