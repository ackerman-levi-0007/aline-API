package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IClinicDoctorRelationshipDao;
import com.aline.aline.entities.ClinicDoctorRelationship;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.repositories.ClinicDoctorRelationshipRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClinicDoctorRelationshipDao implements IClinicDoctorRelationshipDao {

    private final ClinicDoctorRelationshipRepo clinicDoctorRelationshipRepo;

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
    public void addExistingDoctorToClinic(UserDto doctor, UserDto clinic, CreateClinicDoctorRelationShip clinicDoctorRelationShip) throws BadRequestException {
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

    @Override
    public List<String> getDoctorIdsForClinicId(String clinicID) {
        List<ClinicDoctorRelationship> clinicDoctorRelationshipList = this.clinicDoctorRelationshipRepo.findAllByClinicID(clinicID);
        return clinicDoctorRelationshipList.stream().map(
                ClinicDoctorRelationship::getDoctorID
        ).toList();
    }

    @Override
    public boolean checkDoctorIDRelationshipToClinicID(String doctorID, String clinicID) {
        return checkClinicDoctorRelationshipExists(doctorID, clinicID);
    }

    @Override
    public List<String> getClinicIdsForDoctorID(String doctorID) {
        List<ClinicDoctorRelationship> clinicDoctorRelationshipList = this.clinicDoctorRelationshipRepo.findAllByDoctorID(doctorID);
        return clinicDoctorRelationshipList.stream().map(
                ClinicDoctorRelationship::getClinicID
        ).toList();
    }

    @Override
    public boolean checkStatusForDoctorIDRelationshipToClinicID(String doctorID, String clinicID) {
        return checkClinicDoctorRelationshipStatus(doctorID, clinicID);
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
