package com.aline.aline.services.Impl;

import com.aline.aline.dao.ITokenDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.Token;
import com.aline.aline.entities.User;
import com.aline.aline.enums.TokenType;
import com.aline.aline.payload.AuthenticationRequest;
import com.aline.aline.payload.AuthenticationResponse;
import com.aline.aline.payload.UserDto;
import com.aline.aline.security.JwtService;
import com.aline.aline.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;

    private final IUserDao userDao;

    private final ITokenDao tokenDao;

    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) {
        User user = this.modelMapper.map(authenticationRequest, User.class);
        UserDto userDto = this.userDao.createUser(user);
        User savedUser = this.modelMapper.map(userDto, User.class);
        String token = jwtService.generateToken(savedUser);

        Token generatedToken = new Token(userDto.getId().toString(), token, TokenType.BEARER, false, false);
        this.tokenDao.saveToken(generatedToken);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = this.userDao.findByEmailForLogin(authenticationRequest.getEmail());
        String token = jwtService.generateToken(user);

        Token generatedToken = new Token(user.getId().toString(), token, TokenType.BEARER, false, false);
        this.tokenDao.saveToken(generatedToken);

        return new AuthenticationResponse(token);
    }
}
