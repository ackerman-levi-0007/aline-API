package com.aline.aline.payload.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserIdAndNameDto {
    private String userID;
    private String userName;
}
