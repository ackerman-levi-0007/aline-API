package com.aline.aline.services;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientService {
    GetPatientDto createPatient(Patient patient);
    List<GetPatientDto> getAllPatients();
}
