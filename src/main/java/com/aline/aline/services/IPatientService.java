package com.aline.aline.services;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.Patient.*;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IPatientService {
    GetPatientDto createPatient(Patient patient) throws BadRequestException;
    Page<GetPatientWithProfileDto> getAllPatients(PageDto pageDto, FilterPatientDto filterPatientDto);
    GetPatientDto getPatientByID(String patientID);
    GetPatientDto updatePatient(UpdatePatientDto patient) throws BadRequestException;
    void updatePatientStatus(UpdatePatientStatusDto patientStatus);
    void deletePatient(String patientID);
    void changeDoctorAllocationForPatient(UpdateDoctorAllocationDto doctorAllocationDto);
    GetUserDetailsForPatientDto getUserDetailsForPatientID(String patientID);
}
