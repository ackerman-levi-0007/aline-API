package com.aline.aline.payload.User;

import com.aline.aline.entities.User;
import com.aline.aline.entities.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreationDto {
    private User user;
    private UserDetails userDetails;
}
