package com.aline.aline.payload;

import com.aline.aline.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UserRole> role;
    private Boolean status = true;
}
