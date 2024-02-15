package com.aline.aline.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class Patient {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private String id;

    private String firstName;

    private String lastName;


}
