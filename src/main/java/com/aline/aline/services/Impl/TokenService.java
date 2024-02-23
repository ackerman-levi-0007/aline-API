package com.aline.aline.services.Impl;

import com.aline.aline.dao.ITokenDao;
import com.aline.aline.entities.Token;
import com.aline.aline.services.ITokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    private final ITokenDao tokenDao;

    @Override
    public boolean isTokenValid(String token) {
       Optional<Token> fetchedTokenDetails = this.tokenDao.getTokenDetailsByToken(token);
        return fetchedTokenDetails.map(value -> !value.isExpired() && !value.isRevoked()).orElse(true);
    }
}
