package com.aline.aline.services.Impl;

import com.aline.aline.dao.ITokenDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.User;
import com.aline.aline.payload.Authentication.AuthenticationLoginRequest;
import com.aline.aline.payload.Authentication.AuthenticationRegisterRequest;
import com.aline.aline.payload.Authentication.AuthenticationResponse;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.security.JwtService;
import com.aline.aline.services.IAuthenticationService;
import com.aline.aline.services.ITokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final IUserDao userDao;
    private final ITokenDao tokenDao;
    private final ITokenService tokenService;

    public AuthenticationResponse register(AuthenticationRegisterRequest authenticationRequest) {
        User user = this.modelMapper.map(authenticationRequest, User.class);
        UserDto userDto = this.userDao.createUser(user, null);
        User savedUser = this.modelMapper.map(userDto, User.class);

        return tokenService.generateToken(user.getEmail());
    }

    public AuthenticationResponse login(AuthenticationLoginRequest authenticationLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationLoginRequest.getEmail(),
                        authenticationLoginRequest.getPassword()
                )
        );
        return tokenService.generateToken(authenticationLoginRequest.getEmail());
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            User user = this.userDao.findByEmailForLogin(userEmail);
            if(jwtService.isTokenValid(refreshToken, user)){
                tokenDao.revokeAllUserTokens(user.getId().toString());
                new ObjectMapper().writeValue(response.getOutputStream(), tokenService.generateToken(userEmail));
            }
        }
    }
}
