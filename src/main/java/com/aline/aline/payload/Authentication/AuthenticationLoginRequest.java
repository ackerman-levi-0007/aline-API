package com.aline.aline.payload.Authentication;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationLoginRequest {
    @Email
    private String email;
    private String password;
}
