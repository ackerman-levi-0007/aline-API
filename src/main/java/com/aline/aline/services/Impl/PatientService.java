package com.aline.aline.services.Impl;

import com.aline.aline.dao.IPatientDao;
import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.services.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientDao patientDao;

    @Override
    public GetPatientDto createPatient(Patient patient) {
        return this.patientDao.createPatient(patient);
    }

    @Override
    public List<GetPatientDto> getAllPatients() {
        return this.patientDao.getAllPatients();
    }
}
