package com.aline.aline.repositories;

import com.aline.aline.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends MongoRepository<Token, String> {
    @Query("{ 'userID' : ?0, $or: [{'expired' : false}, {'revoked' : false }] }")
    List<Token> findAllValidTokenByUser(String userID);

    Optional<Token> findByAccessToken(String token);

    List<Token> findByAccessTokenOrRefreshToken(String accessToken, String refreshToken);
}
