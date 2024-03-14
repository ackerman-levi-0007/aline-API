package com.aline.aline.controllers;

import com.aline.aline.payload.Authentication.AuthenticationLoginRequest;
import com.aline.aline.payload.Authentication.AuthenticationRegisterRequest;
import com.aline.aline.payload.Authentication.AuthenticationResponse;
import com.aline.aline.services.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/aline/auth")
@Tag(name = "AuthController", description = "This API validate the login details and provides the login token for the user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRegisterRequest authenticationRequest
    ){
        return new ResponseEntity<>(authenticationService.register(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody AuthenticationLoginRequest authenticationLoginRequest
    ){
        return new ResponseEntity<>(authenticationService.login(authenticationLoginRequest), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
