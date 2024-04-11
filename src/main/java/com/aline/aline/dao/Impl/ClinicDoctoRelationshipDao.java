package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IClinicDoctorRelationshipDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.ClinicDoctorRelationship;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.repositories.ClinicDoctorRelationshipRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClinicDoctoRelationshipDao implements IClinicDoctorRelationshipDao {

    private ClinicDoctorRelationshipRepo clinicDoctorRelationshipRepo;
    private IUserDao userDao;

    @Autowired
    public ClinicDoctoRelationshipDao(ClinicDoctorRelationshipRepo clinicDoctorRelationshipRepo){
        super();
        this.clinicDoctorRelationshipRepo = clinicDoctorRelationshipRepo;
    }

    @Autowired
    public void setUserDao(@Lazy IUserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void create(String doctorID, String clinicID, boolean status) {
        ClinicDoctorRelationship clinicDoctorRelationship = new ClinicDoctorRelationship(
                doctorID, clinicID, status
        );
        this.clinicDoctorRelationshipRepo.save(clinicDoctorRelationship);

    }

    @Override
    public ClinicDoctorRelationship create(ClinicDoctorRelationship clinicDoctorRelationship) {
        return this.clinicDoctorRelationshipRepo.save(clinicDoctorRelationship);
    }

    @Override
    public void addExistingDoctorToClinic(CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException {
        UserDto doctor = this.userDao.getUserByID(clinicDoctorRelationShip.getDoctorID());
        UserDto clinic = this.userDao.getUserByID(clinicDoctorRelationShip.getClinicID());

        if(doctor.getRole().contains(UserRole.ROLE_DOCTOR) && clinic.getRole().contains(UserRole.ROLE_CLINIC)){
            if(!checkClinicDoctorRelationshipExists(clinicDoctorRelationShip.getDoctorID(), clinicDoctorRelationShip.getClinicID())){
                ClinicDoctorRelationship clinicDoctorRelationship = new ClinicDoctorRelationship(
                        doctor.getId(), clinic.getId(), true
                );
                this.clinicDoctorRelationshipRepo.save(clinicDoctorRelationship);
            }
            else{
                if(checkClinicDoctorRelationshipStatus(clinicDoctorRelationShip.getDoctorID(), clinicDoctorRelationShip.getClinicID())){
                    throw new BadRequestException("Doctor is already mapped to the clinic");
                }
                else{
                    throw new BadRequestException("Doctor is already mapped to the clinic and is in deactivate state");
                }
            }
        }
        else{
            throw new BadRequestException("The user provided has not the mentioned role. Please check!!!");
        }
    }

    /*****************************************************************************************
                                                Helpers
     ****************************************************************************************/

    public boolean checkClinicDoctorRelationshipExists(String doctorID, String clinicID){
        Optional<ClinicDoctorRelationship> fetchedRelationship = this.clinicDoctorRelationshipRepo.findByDoctorIDAndClinicID(doctorID, clinicID);
        return fetchedRelationship.isPresent();
    }

    public boolean checkClinicDoctorRelationshipStatus(String doctorID, String clinicID){
        Optional<ClinicDoctorRelationship> fetchedRelationship = this.clinicDoctorRelationshipRepo.findByDoctorIDAndClinicID(doctorID, clinicID);

        if(fetchedRelationship.isPresent()){
            return fetchedRelationship.get().isStatus();
        }
        else{
            throw new ResourceNotFoundException("DoctorID : " + doctorID + " is not mapped with ClinicID : " + clinicID);
        }
    }

}
