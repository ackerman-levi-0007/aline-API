package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IPatientDao;
import com.aline.aline.entities.Patient;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
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

    @Override
    public GetPatientDto getPatientByID(String patientID) {
        Patient patient = getPatient(patientID);
        return this.modelMapper.map(patient, GetPatientDto.class);
    }

    @Override
    public GetPatientDto updatePatient(Patient updatedPatient) {

        Patient patient = getPatient(updatedPatient.getId());

        patient.setName(updatedPatient.getName());
        patient.setStatus(updatedPatient.getStatus());
        patient.setDateOfScan(updatedPatient.getDateOfScan());
        patient.setGender(updatedPatient.getGender());
        patient.setAge(updatedPatient.getAge());
        patient.setNationality(updatedPatient.getNationality());
        patient.setChiefComplaint(updatedPatient.getChiefComplaint());

        Patient savedPatient = this.patientRepo.save(patient);

        return this.modelMapper.map(savedPatient, GetPatientDto.class);
    }

    @Override
    public void updatePatientStatus(UpdatePatientStatusDto patientStatus) {
        Patient patient = getPatient(patientStatus.getPatientID());

        patient.setStatus(patientStatus.getStatus());
        this.patientRepo.save(patient);
    }

    @Override
    public void deletePatient(String patientID) {
        Patient patient = getPatient(patientID);
        this.patientRepo.delete(patient);
    }

    /*****************************************************************************************
                                             Helpers
     *****************************************************************************************/

    public Patient getPatient(String patientID){
        return this.patientRepo.findById(patientID).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "patientID", patientID)
        );
    }

}
