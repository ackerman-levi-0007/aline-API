package com.aline.aline.services;

import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import org.springframework.stereotype.Service;

@Service
public interface IPatientPhotoScansService {
    GetPatientPhotoScansDto updatePatientPhotoScans(PatientPhotoScans patientPhotoScans, int rebootID);
    Object getPatientPhotoScansByPatientID(String patientID, int rebootID);
    GetPatientPhotoScansDto savePatientPhotoScans(PatientPhotoScans patientPhotoScans, int rebootID);
}
