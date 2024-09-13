package com.aline.aline.entities;

import com.aline.aline.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Token")
public class Token {
    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private String userID;

    private String accessToken;

    private String refreshToken;

    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    @CreatedDate
    private Date createdOn;

    public Token(String userID, String accessToken, String refreshToken, TokenType tokenType, boolean expired, boolean revoked) {
        this.userID = userID;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expired = expired;
        this.revoked = revoked;
    }
}
