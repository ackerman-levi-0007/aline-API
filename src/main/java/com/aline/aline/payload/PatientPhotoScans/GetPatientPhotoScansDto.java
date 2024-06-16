package com.aline.aline.payload.PatientPhotoScans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetPatientPhotoScansDto {


    private String patientID;

    private List<String> profilePhoto;

    private List<String> extFront;

    private List<String> extSide;

    private List<String> extOblique;

    private List<String> intFront;

    private List<String> intSideLeft;

    private List<String> intFrontRight;

    private List<String> intMaxilla;

    private List<String> intMandible;

    private List<String> opg;

    private List<String> cep;

    private List<String> scans;
}
