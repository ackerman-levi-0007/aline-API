package com.aline.aline.dao;

import com.aline.aline.entities.ClinicDoctorRelationship;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

@Repository
public interface IClinicDoctorRelationshipDao {
    void  create(String doctorID, String clinicID, boolean status);
    ClinicDoctorRelationship create(ClinicDoctorRelationship clinicDoctorRelationship);
    void addExistingDoctorToClinic(CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException;
}
