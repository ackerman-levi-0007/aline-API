package com.aline.aline.dao.Impl;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.dao.IPatientDao;
import com.aline.aline.entities.Patient;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ForbiddenException;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.Patient.FilterPatientDto;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
import com.aline.aline.repositories.PatientRepo;
import com.aline.aline.utilities.CommonUtils;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PatientDao implements IPatientDao {

    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public GetPatientDto createPatient(Patient patient) {
        Patient savedPatient = this.patientRepo.save(patient);
        return this.modelMapper.map(savedPatient, GetPatientDto.class);
    }

    @Override
    public Page<GetPatientDto> getAllPatients(Pageable pageable, FilterPatientDto filterPatientDto) {
         Query query = new Query();

        User loggedInUser = SecurityUtils.getLoggedInUser();

        if(loggedInUser.getRole().contains(UserRole.ROLE_DOCTOR)) filterPatientDto.setDoctorID(Collections.singletonList(loggedInUser.getId().toString()));
        if(loggedInUser.getRole().contains(UserRole.ROLE_CLINIC)) filterPatientDto.setClinicID(Collections.singletonList(loggedInUser.getId().toString()));

        Criteria dateOfScanCriteria = Criteria.where("dateOfScan");

        if(filterPatientDto.getFromDateOfScan() != null) dateOfScanCriteria.gte(filterPatientDto.getFromDateOfScan());
        if(filterPatientDto.getToDateOfScan() != null) dateOfScanCriteria.lte(filterPatientDto.getToDateOfScan());

        if(filterPatientDto.getFromDateOfScan() != null || filterPatientDto.getToDateOfScan() != null) query.addCriteria(dateOfScanCriteria);

        if(!filterPatientDto.getPatientID().isEmpty()) query.addCriteria(Criteria.where("id").in(filterPatientDto.getPatientID()));
        if(!filterPatientDto.getClinicID().isEmpty()) query.addCriteria(Criteria.where("clinicID").in(filterPatientDto.getClinicID()));
        if(!filterPatientDto.getDoctorID().isEmpty()) query.addCriteria(Criteria.where("doctorID").in(filterPatientDto.getDoctorID()));
        if(filterPatientDto.getName() != null) query.addCriteria(Criteria.where("name").regex(filterPatientDto.getName(), "i"));
        if(!CommonUtils.isNullOrEmpty(filterPatientDto.getGender())) query.addCriteria(Criteria.where("gender").is(filterPatientDto.getGender()));
        if(!CommonUtils.isNullOrEmpty(filterPatientDto.getStatus())) query.addCriteria(Criteria.where("status").is(filterPatientDto.getStatus()));
        if(filterPatientDto.getNationality() != null) query.addCriteria(Criteria.where("nationality").regex(filterPatientDto.getNationality(), "i"));

        Criteria ageCriteria = Criteria.where("age");

        if(filterPatientDto.getFromAge() != 0) ageCriteria.gte(filterPatientDto.getFromAge());
        if(filterPatientDto.getToAge() != 0) ageCriteria.lte(filterPatientDto.getToAge());

        if(filterPatientDto.getFromAge() != 0 || filterPatientDto.getToAge() != 0) query.addCriteria(ageCriteria);

        long totalCount = this.mongoTemplate.count(query, Patient.class);
        query.with(pageable);

        List<Patient> patientList = this.mongoTemplate.find(query, Patient.class);
        List<GetPatientDto> getPatientDtoList = patientList.stream().map(x -> this.modelMapper.map(x, GetPatientDto.class)).toList();

        return new PageImpl<>(
                getPatientDtoList,
                pageable,
                totalCount
        );
    }

    @Override
    public GetPatientDto getPatientByID(String patientID) {
        Patient patient = getPatientByPatientIDForLoggedInUser(patientID);
        return this.modelMapper.map(patient, GetPatientDto.class);
    }

    @Override
    public GetPatientDto updatePatient(Patient updatedPatient) {

        Patient patient = getPatient(updatedPatient.getId());

        patient.setFilePatientID(updatedPatient.getFilePatientID());
        patient.setLabPatientID(updatedPatient.getLabPatientID());
        patient.setClinicID(updatedPatient.getClinicID());
        patient.setDoctorID(updatedPatient.getDoctorID());
        patient.setName(updatedPatient.getName());
        patient.setStatus(updatedPatient.getStatus());
        patient.setDateOfScan(updatedPatient.getDateOfScan());
        patient.setGender(updatedPatient.getGender());
        patient.setAge(updatedPatient.getAge());
        patient.setNationality(updatedPatient.getNationality());

        Patient savedPatient = this.patientRepo.save(patient);

        return this.modelMapper.map(savedPatient, GetPatientDto.class);
    }

    @Override
    public void updatePatientStatus(UpdatePatientStatusDto patientStatus) {
        Patient patient = getPatientByPatientIDForLoggedInUser(patientStatus.getPatientID());
        patient.setStatus(patientStatus.getStatus());
        this.patientRepo.save(patient);
    }

    @Override
    public void deletePatient(String patientID) {
        Patient patient = getPatientByPatientIDForLoggedInUser(patientID);
        this.patientRepo.delete(patient);
    }

    @Override
    public void changeDoctorAllocationForPatient(String patientID, String doctorID) {
        Patient patient = getPatient(patientID);
        patient.setDoctorID(doctorID);
        this.patientRepo.save(patient);
    }

    @Override
    public void updatePatientProfilePhoto(String patientID, S3ImageObject profilePhoto) {
        String profilePhotoURL = null;
        if(profilePhoto != null && !CommonUtils.isNullOrEmpty(profilePhoto.getURL())){
            profilePhotoURL = profilePhoto.getURL();
        }

        Patient patient = getPatient(patientID);
        patient.setProfilePhoto(profilePhotoURL);
        this.patientRepo.save(patient);
    }

    /*****************************************************************************************
                                             Helpers
     *****************************************************************************************/

    public Patient getPatientByPatientIDForLoggedInUser(String patientID){
        Patient patient;

        User loggedInUser = SecurityUtils.getLoggedInUser();

        if(loggedInUser.getRole().contains(UserRole.ROLE_ADMIN) || loggedInUser.getRole().contains(UserRole.ROLE_LAB)){
            patient = getPatient(patientID);
        } else if (loggedInUser.getRole().contains(UserRole.ROLE_CLINIC)) {
            patient = getPatientByPatientIDAndClinicID(patientID, loggedInUser.getId().toString());
        } else if (loggedInUser.getRole().contains(UserRole.ROLE_DOCTOR)) {
            patient = getPatientByPatientIDAndDoctorID(patientID, loggedInUser.getId().toString());
        } else {
            throw new ForbiddenException("User does not have access to view the data. Please contact with administrator.");
        }

        return patient;
    }

    public Patient getPatient(String patientID){
        return this.patientRepo.findById(patientID).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "patientID", patientID)
        );
    }

    public Patient getPatientByPatientIDAndDoctorID(String patientID, String doctorID) {
        return this.patientRepo.findByIdAndDoctorID(patientID, doctorID).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "patientID", patientID)
        );
    }

    public Patient getPatientByPatientIDAndClinicID(String patientID, String clinicID) {
        return this.patientRepo.findByIdAndClinicID(patientID, clinicID).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "patientID", patientID)
        );
    }

}
