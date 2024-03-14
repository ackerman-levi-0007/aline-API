package com.aline.aline.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "UserDetails")
public class UserDetails {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String userID;

    @NotEmpty(message = "Mobile number cannot be empty.")
    @Length(max = 15, message = "Please enter a valid mobile number.")
    private String mobileNo;

    private String userAddress;

    private String userCity;

    private String userCountry;
}
