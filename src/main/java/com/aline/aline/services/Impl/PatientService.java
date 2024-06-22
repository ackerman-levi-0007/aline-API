package com.aline.aline.services.Impl;

import com.aline.aline.dao.*;
import com.aline.aline.entities.Patient;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.Patient.*;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.payload.User.UserIdAndNameDto;
import com.aline.aline.services.IPatientService;
import com.aline.aline.utilities.PageUtils;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientDao patientDao;
    private final ModelMapper modelMapper;
    private final IUserDao userDao;
    private final IClinicDoctorRelationshipDao clinicDoctorRelationshipDao;
    private final IPatientDentalDetailsDao patientDentalDetailsDao;
    private final IPatientPhotoScansDao patientPhotoScansDao;

    @Override
    public GetPatientDto createPatient(Patient patient) throws BadRequestException {
        checkPatientObject(patient);
        return this.patientDao.createPatient(patient);
    }

    @Override
    public Page<GetPatientWithProfileDto> getAllPatients(PageDto pageDto, FilterPatientDto filterPatientDto) {
        Pageable pageable = PageUtils.getPageableFromPageDto(pageDto);
        UserDto loggedInUser = this.userDao.getUserByID(Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString());
        Page<GetPatientDto> patientDtos = this.patientDao.getAllPatients(pageable, filterPatientDto, loggedInUser);

        List<GetPatientWithProfileDto> patientWithProfileDtos = patientDtos.stream().map(
                patientDto ->{
                    String  profilePhoto = this.patientPhotoScansDao.getPatientProfilePhotoByPatientID(patientDto.getId());

                    GetPatientWithProfileDto patientWithProfileDto = this.modelMapper.map(patientDto, GetPatientWithProfileDto.class);
                    patientWithProfileDto.setProfilePhoto(profilePhoto);

                    return patientWithProfileDto;
                }
        ).toList();

        return new PageImpl<>(patientWithProfileDtos, patientDtos.getPageable(), patientDtos.getTotalElements());
    }

    @Override
    public GetPatientDto getPatientByID(String patientID) {
        UserDto loggedInUser = this.userDao.getUserByID(Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString());
        return this.patientDao.getPatientByID(patientID, loggedInUser);
    }

    @Override
    public GetPatientDto updatePatient(UpdatePatientDto patient) throws BadRequestException {
        Patient updatedPatient = this.modelMapper.map(patient, Patient.class);

        checkPatientObject(updatedPatient);
        return this.patientDao.updatePatient(updatedPatient);
    }

    @Override
    public void updatePatientStatus(UpdatePatientStatusDto patientStatus) {
        UserDto loggedInUser = this.userDao.getUserByID(Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString());
        this.patientDao.updatePatientStatus(patientStatus, loggedInUser);
    }

    @Override
    public void deletePatient(String patientID) {
        UserDto loggedInUser = this.userDao.getUserByID(Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString());
        this.patientDentalDetailsDao.deletePatientDentalDetailByPatientID(patientID);
        this.patientDao.deletePatient(patientID, loggedInUser);
    }

    @Override
    public void changeDoctorAllocationForPatient(UpdateDoctorAllocationDto doctorAllocationDto) {
         this.patientDao.changeDoctorAllocationForPatient(
                 doctorAllocationDto.getPatientID(), doctorAllocationDto.getNewDoctorID()
         );
    }

    @Override
    public GetUserDetailsForPatientDto getUserDetailsForPatientID(String patientID) {
        GetPatientDto getPatientDto = getPatientByID(patientID);
        String patientProfilePhoto = this.patientPhotoScansDao.getPatientProfilePhotoByPatientID(patientID);

        UserDto clinicDto = this.userDao.getUserByID(getPatientDto.getClinicID());
        UserDto doctorDto = this.userDao.getUserByID(getPatientDto.getDoctorID());

        GetUserDetailsForPatientDto getUserDetailsForPatientDto = new GetUserDetailsForPatientDto();

        getUserDetailsForPatientDto.setPatient(new UserIdAndNameDto(getPatientDto.getId(), getPatientDto.getName()));
        getUserDetailsForPatientDto.setDoctor(new UserIdAndNameDto(doctorDto.getId(), doctorDto.getName()));
        getUserDetailsForPatientDto.setClinic(new UserIdAndNameDto(clinicDto.getId(), clinicDto.getName()));
        getUserDetailsForPatientDto.setPatientStatus(getUserDetailsForPatientDto.getPatientStatus());
        getUserDetailsForPatientDto.setPatientProfilePhoto(patientProfilePhoto);

        return getUserDetailsForPatientDto;
    }

    /*****************************************************************************************
                                            Helpers
     ****************************************************************************************/

    public void checkPatientObject(Patient patient) throws BadRequestException {
        String loggedInUserID = Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString();
        UserDto user = this.userDao.getUserByID(loggedInUserID);

        if(user.getRole().contains(UserRole.ROLE_CLINIC)){
            if(!loggedInUserID.equals(patient.getClinicID())) throw new BadRequestException("ClinicId provided does not match with the logged in clinic");
        }

        if(user.getRole().contains(UserRole.ROLE_DOCTOR)){
            if(!loggedInUserID.equals(patient.getDoctorID())) throw new BadRequestException("DoctorID provided does not match with the logged in doctor");
        }

        boolean clinicDoctorRelationshipStatus = this.clinicDoctorRelationshipDao.checkStatusForDoctorIDRelationshipToClinicID(patient.getDoctorID(), patient.getClinicID());

        if(!clinicDoctorRelationshipStatus){
            throw new BadRequestException("Doctor is not active inside the mapped the clinic. Please reactivate the doctor account and then map the patient to provided clinic and doctor.");
        }
    }
}
