package com.aline.aline.dao;

import com.aline.aline.entities.ClinicDoctorRelationship;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import com.aline.aline.payload.User.UserDto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClinicDoctorRelationshipDao {
    void  create(String doctorID, String clinicID, boolean status);
    ClinicDoctorRelationship create(ClinicDoctorRelationship clinicDoctorRelationship);
    void addExistingDoctorToClinic(UserDto doctor, UserDto clinic, CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException;
    List<String> getDoctorIdsForClinicId(String clinicID);
    boolean checkDoctorIDRelationshipToClinicID(String doctorID, String clinicID);
    List<String> getClinicIdsForDoctorID(String doctorID);
    boolean checkStatusForDoctorIDRelationshipToClinicID(String doctorID, String clinicID);
}
