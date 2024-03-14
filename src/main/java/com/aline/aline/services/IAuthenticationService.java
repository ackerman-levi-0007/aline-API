package com.aline.aline.services;

import com.aline.aline.payload.Authentication.AuthenticationLoginRequest;
import com.aline.aline.payload.Authentication.AuthenticationRegisterRequest;
import com.aline.aline.payload.Authentication.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IAuthenticationService {
    AuthenticationResponse register(AuthenticationRegisterRequest authenticationRequest);
    AuthenticationResponse login(AuthenticationLoginRequest authenticationLoginRequest);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
