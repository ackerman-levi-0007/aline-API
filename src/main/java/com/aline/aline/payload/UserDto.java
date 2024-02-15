package com.aline.aline.payload;

import com.aline.aline.entities.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;

    private String firstName;

    private String lastName;

    @Email
    @NotEmpty
    private String email;

    private String orgName;

    private String parentID;

    private List<UserRole> role;

    private Boolean status;
}
