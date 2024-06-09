package com.aline.aline.services;

import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import org.springframework.stereotype.Service;

@Service
public interface IPatientPhotoScansService {
    GetPatientPhotoScansDto updatePatientPhotoScans(PatientPhotoScans patientPhotoScans);
    Object getPatientPhotoScansByPatientID(String patientID);
    GetPatientPhotoScansDto savePatientPhotoScans(PatientPhotoScans patientPhotoScans);
}
