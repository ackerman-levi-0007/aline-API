package com.aline.aline.services.Impl;

import com.aline.aline.dao.IClinicDoctorRelationshipDao;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import com.aline.aline.services.IClinicDoctorRelationshipService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicDoctorRelationshipService implements IClinicDoctorRelationshipService {

    private final IClinicDoctorRelationshipDao clinicDoctorRelationshipDao;

    @Override
    public void addExistingDoctorToClinic(CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException {
        this.clinicDoctorRelationshipDao.addExistingDoctorToClinic(clinicDoctorRelationShip);
    }
}
