package com.aline.aline.payload.ClinicDoctorRelationShip.PostDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClinicDoctorRelationShip {
    private String doctorID;
    private String clinicID;
}
