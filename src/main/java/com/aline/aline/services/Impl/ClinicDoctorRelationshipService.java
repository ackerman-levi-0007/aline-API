package com.aline.aline.services.Impl;

import com.aline.aline.dao.IClinicDoctorRelationshipDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.services.IClinicDoctorRelationshipService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicDoctorRelationshipService implements IClinicDoctorRelationshipService {

    private final IClinicDoctorRelationshipDao clinicDoctorRelationshipDao;
    private final IUserDao userDao;

    @Override
    public void addExistingDoctorToClinic(CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException {
        UserDto doctor = this.userDao.getUserByID(clinicDoctorRelationShip.getDoctorID());
        UserDto clinic = this.userDao.getUserByID(clinicDoctorRelationShip.getClinicID());
        this.clinicDoctorRelationshipDao.addExistingDoctorToClinic(doctor, clinic, clinicDoctorRelationShip);
    }
}
