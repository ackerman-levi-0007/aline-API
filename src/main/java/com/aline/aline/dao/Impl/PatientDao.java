package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IPatientDao;
import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.repositories.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PatientDao implements IPatientDao {

    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;

    @Override
    public GetPatientDto createPatient(Patient patient) {
        Patient savedPatient = this.patientRepo.save(patient);
        return this.modelMapper.map(savedPatient, GetPatientDto.class);
    }

    @Override
    public List<GetPatientDto> getAllPatients() {
        List<Patient> patientList = this.patientRepo.findAll();
        return patientList.stream().map(x -> this.modelMapper.map(x, GetPatientDto.class)).toList();
    }
}
