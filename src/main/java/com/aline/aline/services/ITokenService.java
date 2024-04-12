package com.aline.aline.services;

import com.aline.aline.payload.Authentication.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface ITokenService {
    boolean isTokenValid(String accessToken, String refreshToken);

    AuthenticationResponse generateToken(String email);
}
