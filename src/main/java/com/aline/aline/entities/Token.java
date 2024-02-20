package com.aline.aline.entities;

import com.aline.aline.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Token")
public class Token {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;
    private ObjectId userID;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
}
