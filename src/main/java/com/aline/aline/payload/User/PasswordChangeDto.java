package com.aline.aline.payload.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto {
    private String currentPassword;
    private String newPassword;
    private String reEnterNewPassword;
}
