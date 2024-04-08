package com.aline.aline.services;

import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface IClinicDoctorRelationshipService {
    void addExistingDoctorToClinic(CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException;
}
