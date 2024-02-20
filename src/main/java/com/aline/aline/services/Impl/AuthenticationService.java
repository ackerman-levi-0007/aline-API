package com.aline.aline.services.Impl;

import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.User;
import com.aline.aline.payload.AuthenticationRequest;
import com.aline.aline.payload.AuthenticationResponse;
import com.aline.aline.payload.UserDto;
import com.aline.aline.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;

    private final IUserDao userDao;

    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) {
        User user = this.modelMapper.map(authenticationRequest, User.class);
        UserDto userDto = this.userDao.createUser(user);
        User savedUser = this.modelMapper.map(userDto, User.class);
        String token = jwtService.generateToken(savedUser);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userRepo.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
