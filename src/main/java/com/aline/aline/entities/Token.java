package com.aline.aline.entities;

import com.aline.aline.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String token;

    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    @CreatedDate
    private Date createdOn;

    public Token(String userID, String token, TokenType tokenType, boolean expired, boolean revoked) {
        this.userID = userID;
        this.token = token;
        this.tokenType = tokenType;
        this.expired = expired;
        this.revoked = revoked;
    }
}
