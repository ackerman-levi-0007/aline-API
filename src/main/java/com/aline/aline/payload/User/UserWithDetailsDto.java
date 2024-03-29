package com.aline.aline.payload.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithDetailsDto {
    UserDto userDto;
    UserDetailsDto userDetailsDto;
}
