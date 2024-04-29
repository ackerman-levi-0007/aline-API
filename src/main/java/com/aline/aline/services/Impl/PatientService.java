package com.aline.aline.services.Impl;

import com.aline.aline.dao.IPatientDao;
import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdateDoctorAllocationDto;
import com.aline.aline.payload.Patient.UpdatePatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
import com.aline.aline.services.IPatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientDao patientDao;
    private final ModelMapper modelMapper;

    @Override
    public GetPatientDto createPatient(Patient patient) {
        return this.patientDao.createPatient(patient);
    }

    @Override
    public List<GetPatientDto> getAllPatients() {
        return this.patientDao.getAllPatients();
    }

    @Override
    public GetPatientDto getPatientByID(String patientID) {
        return this.patientDao.getPatientByID(patientID);
    }

    @Override
    public GetPatientDto updatePatient(UpdatePatientDto patient) {
        Patient updatedPatient = this.modelMapper.map(patient, Patient.class);
        return this.patientDao.updatePatient(updatedPatient);
    }

    @Override
    public void updatePatientStatus(UpdatePatientStatusDto patientStatus) {
        this.patientDao.updatePatientStatus(patientStatus);
    }

    @Override
    public void deletePatient(String patientID) {
        this.patientDao.deletePatient(patientID);
    }

    @Override
    public void changeDoctorAllocationForPatient(UpdateDoctorAllocationDto doctorAllocationDto) {

    }
}
