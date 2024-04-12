package com.aline.aline.dao;

import com.aline.aline.entities.Token;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenDao {
    void saveToken(Token token);
    void revokeAllUserTokens(String userID);
    void revokeToken(String token);
    Optional<Token> getTokenDetailsByToken(String accessToken, String refreshToken);
}
