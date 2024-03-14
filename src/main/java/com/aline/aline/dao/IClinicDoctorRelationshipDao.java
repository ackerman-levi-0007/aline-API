package com.aline.aline.dao;

import com.aline.aline.entities.ClinicDoctorRelationship;
import org.springframework.stereotype.Repository;

@Repository
public interface IClinicDoctorRelationshipDao {
    public ClinicDoctorRelationship create(ClinicDoctorRelationship clinicDoctorRelationship);
}
