package com.aline.aline.dao;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientDao {
    GetPatientDto createPatient(Patient patient);
    List<GetPatientDto> getAllPatients();
    GetPatientDto getPatientByID(String patientID);
    GetPatientDto updatePatient(Patient updatedPatient);
    void updatePatientStatus(UpdatePatientStatusDto patientStatus);
    void deletePatient(String patientID);
    void changeDoctorAllocationForPatient(String patientID, String doctorID);
}
