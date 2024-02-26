package com.aline.aline.dao.Impl;

import com.aline.aline.dao.ITokenDao;
import com.aline.aline.entities.Token;
import com.aline.aline.repositories.TokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenDao implements ITokenDao {

    private final TokenRepo tokenRepo;

    @Override
    public void saveToken(Token token) {
        this.tokenRepo.save(token);
    }

    @Override
    public void revokeAllUserTokens(String userID) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(userID);
        if(validUserTokens == null) return;

        validUserTokens.forEach(x -> {
            x.setRevoked(true);
            x.setExpired(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    @Override
    public void revokeToken(String token) {
        Optional<Token> fetchedToken = tokenRepo.findByToken(token);
        if(fetchedToken.isPresent()){
            fetchedToken.get().setRevoked(true);
            fetchedToken.get().setExpired(true);
            tokenRepo.save(fetchedToken.get());
        }
    }

    @Override
    public Optional<Token> getTokenDetailsByToken(String token) {
        return tokenRepo.findByToken(token);
    }
}
