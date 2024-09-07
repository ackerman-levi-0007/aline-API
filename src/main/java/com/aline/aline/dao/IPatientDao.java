package com.aline.aline.dao;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.Patient.FilterPatientDto;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
import com.aline.aline.payload.User.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientDao {
    GetPatientDto createPatient(Patient patient);
    Page<GetPatientDto> getAllPatients(Pageable pageable, FilterPatientDto filterPatientDto);
    GetPatientDto getPatientByID(String patientID);
    GetPatientDto updatePatient(Patient updatedPatient);
    void updatePatientStatus(UpdatePatientStatusDto patientStatus);
    void deletePatient(String patientID);
    void changeDoctorAllocationForPatient(String patientID, String doctorID);
}
