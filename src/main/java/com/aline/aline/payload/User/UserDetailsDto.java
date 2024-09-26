package com.aline.aline.payload.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
