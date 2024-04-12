package com.aline.aline.dao.Impl;

import com.aline.aline.dao.ITokenDao;
import com.aline.aline.entities.Token;
import com.aline.aline.repositories.TokenRepo;
import com.aline.aline.utilities.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenDao implements ITokenDao {

    private final TokenRepo tokenRepo;
    private final MongoTemplate mongoTemplate;

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
        List<Token> fetchedToken = tokenRepo.findByAccessTokenOrRefreshToken(token, token);
        for(Token getToken : fetchedToken){
            getToken.setRevoked(true);
            getToken.setExpired(true);
            tokenRepo.save(getToken);
        }
    }

    @Override
    public Optional<Token> getTokenDetailsByToken(String accessToken, String refreshToken) {
        Query query = new Query();

        if(!CommonUtils.isNullOrEmpty(accessToken)) query.addCriteria(Criteria.where("accessToken").is(accessToken));
        if(!CommonUtils.isNullOrEmpty(refreshToken)) query.addCriteria(Criteria.where("refreshToken").is(refreshToken));

        List<Token> tokenList = this.mongoTemplate.find(query, Token.class);

        return tokenList.isEmpty() ? Optional.empty() : tokenList.stream().findFirst();
    }
}
