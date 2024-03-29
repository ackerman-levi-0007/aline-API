package com.aline.aline.payload.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String id;

    private String userID;

    private String mobileNo;

    private String userAddress;

    private String userCity;

    private String userCountry;
}
