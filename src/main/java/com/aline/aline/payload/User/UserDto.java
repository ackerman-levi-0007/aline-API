package com.aline.aline.payload.User;

import com.aline.aline.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;

    private String name;

    @Email
    @NotEmpty
    private String email;

    private List<UserRole> role;

    private Date createdOn;
}
