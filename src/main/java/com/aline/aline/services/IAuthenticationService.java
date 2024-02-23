package com.aline.aline.services;

import com.aline.aline.payload.AuthenticationRequest;
import com.aline.aline.payload.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {
    AuthenticationResponse register(AuthenticationRequest authenticationRequest);
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
