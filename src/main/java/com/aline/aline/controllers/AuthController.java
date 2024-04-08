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

    /**
     * Generate a JSON web token if username and password has been authenticated by the BasicAuthenticationFilter.
     * In summary, this filter is responsible for processing any request that has an HTTP request header of Authorization
     * with an authentication scheme of Basic and a Base64-encoded username:password token.
     * <p>
     * BasicAuthenticationFilter will prepare the Authentication object for this login method.
     * Note: before this login method gets called, Spring Security already authenticated the username and password through Basic Auth.
     * Only successful authentication can make it to this method.
     *
     * @return JSON web access token and refresh token
     */
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
