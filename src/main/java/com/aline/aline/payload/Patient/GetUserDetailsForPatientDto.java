package com.aline.aline.payload.Patient;

import com.aline.aline.payload.User.UserIdAndNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUserDetailsForPatientDto {
    private UserIdAndNameDto patient;
    private UserIdAndNameDto doctor;
    private UserIdAndNameDto clinic;
    private String patientStatus;
    private String patientProfilePhoto;
}
