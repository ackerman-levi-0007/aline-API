package com.aline.aline.dao;

import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientPhotoScansDao {
    GetPatientPhotoScansDto updatePatientPhotoScans(PatientPhotoScans patientPhotoScans);
    GetPatientPhotoScansDto getPatientPhotoScansByPatientID(String patientID);
    GetPatientPhotoScansDto savePatientPhotoScans(PatientPhotoScans patientPhotoScans);
}
