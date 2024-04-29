package com.aline.aline.services;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdateDoctorAllocationDto;
import com.aline.aline.payload.Patient.UpdatePatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService {
    GetPatientDto createPatient(Patient patient);
    List<GetPatientDto> getAllPatients();
    GetPatientDto getPatientByID(String patientID);
    GetPatientDto updatePatient(UpdatePatientDto patient);
    void updatePatientStatus(UpdatePatientStatusDto patientStatus);
    void deletePatient(String patientID);
    void changeDoctorAllocationForPatient(UpdateDoctorAllocationDto doctorAllocationDto);
}
