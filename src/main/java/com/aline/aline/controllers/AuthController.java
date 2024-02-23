package com.aline.aline.controllers;

import com.aline.aline.payload.AuthenticationRequest;
import com.aline.aline.payload.AuthenticationResponse;
import com.aline.aline.services.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/aline/auth")
@Tag(name = "AuthController", description = "This API validate the login details and provides the login token for the user")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        return new ResponseEntity<>(authenticationService.register(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest authenticationRequest
    ){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }
}
