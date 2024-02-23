package com.aline.aline.services.Impl;

import com.aline.aline.dao.ITokenDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.Token;
import com.aline.aline.entities.User;
import com.aline.aline.enums.TokenType;
import com.aline.aline.payload.AuthenticationResponse;
import com.aline.aline.security.JwtService;
import com.aline.aline.services.ITokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    private final ITokenDao tokenDao;
    private final JwtService jwtService;
    private final IUserDao userDao;
    @Override
    public boolean isTokenValid(String token) {
       Optional<Token> fetchedTokenDetails = this.tokenDao.getTokenDetailsByToken(token);
        return fetchedTokenDetails.map(value -> !value.isExpired() && !value.isRevoked()).orElse(true);
    }

    @Override
    public AuthenticationResponse generateToken(String email) {
        User user = userDao.findByEmailForLogin(email);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token generatedToken = new Token(user.getId().toString(), token, TokenType.BEARER, false, false);
        this.tokenDao.saveToken(generatedToken);

        return new AuthenticationResponse(token, refreshToken);
    }


}
