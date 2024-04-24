package com.aline.aline.dao;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientDao {
    GetPatientDto createPatient(Patient patient);
    List<GetPatientDto> getAllPatients();
}
