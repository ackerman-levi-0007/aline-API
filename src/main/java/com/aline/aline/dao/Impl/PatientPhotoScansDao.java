package com.aline.aline.dao.Impl;

import com.aline.aline.CustomMapper.GetPatientPhotoScansDtoMapper;
import com.aline.aline.dao.IPatientPhotoScansDao;
import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import com.aline.aline.repositories.IPatientPhotoScansRepo;
import com.aline.aline.utilities.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientPhotoScansDao implements IPatientPhotoScansDao {

    private final IPatientPhotoScansRepo patientPhotoScansRepo;
    private final GetPatientPhotoScansDtoMapper getPatientPhotoScansDtoMapper;

    @Override
    public  GetPatientPhotoScansDto updatePatientPhotoScans(PatientPhotoScans patientPhotoScans) {
        Optional<PatientPhotoScans> fetchedPatientPhotoScansOptional =
                this.patientPhotoScansRepo.findByPatientID(patientPhotoScans.getPatientID());

        if(fetchedPatientPhotoScansOptional.isPresent()){
            PatientPhotoScans fetchedPatientPhotoScans = fetchedPatientPhotoScansOptional.get();

            fetchedPatientPhotoScans.setProfilePhoto(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getProfilePhoto(), patientPhotoScans.getProfilePhoto()));
            fetchedPatientPhotoScans.setExtFront(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getExtFront(),patientPhotoScans.getExtFront()));
            fetchedPatientPhotoScans.setExtSide(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getExtSide(),patientPhotoScans.getExtSide()));
            fetchedPatientPhotoScans.setExtOblique(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getExtOblique(),patientPhotoScans.getExtOblique()));
            fetchedPatientPhotoScans.setIntFront(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getIntFront(),patientPhotoScans.getIntFront()));
            fetchedPatientPhotoScans.setIntSideLeft(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getIntSideLeft(),patientPhotoScans.getIntSideLeft()));
            fetchedPatientPhotoScans.setIntFrontRight(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getIntFrontRight(),patientPhotoScans.getIntFrontRight()));
            fetchedPatientPhotoScans.setIntMaxilla(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getIntMaxilla(),patientPhotoScans.getIntMaxilla()));
            fetchedPatientPhotoScans.setIntMandible(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getIntMandible(),patientPhotoScans.getIntMandible()));
            fetchedPatientPhotoScans.setOpg(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getOpg(),patientPhotoScans.getOpg()));
            fetchedPatientPhotoScans.setCep(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getCep(),patientPhotoScans.getCep()));
            fetchedPatientPhotoScans.setScans(CommonUtils.getUpdateListForS3Images(
                    fetchedPatientPhotoScans.getScans(),patientPhotoScans.getScans()));

            PatientPhotoScans savedPatientPhotoScans =
                    this.patientPhotoScansRepo.save(fetchedPatientPhotoScans);

            return this.getPatientPhotoScansDtoMapper.apply(savedPatientPhotoScans);
        }else{
            return savePatientPhotoScans(patientPhotoScans);
        }
    }

    @Override
    public GetPatientPhotoScansDto getPatientPhotoScansByPatientID(String patientID) {
        return this.patientPhotoScansRepo.findByPatientID(patientID)
                .map(getPatientPhotoScansDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Patient photo scans", "patientID", patientID));
    }

    @Override
    public GetPatientPhotoScansDto savePatientPhotoScans(PatientPhotoScans patientPhotoScans) {
        PatientPhotoScans savedPatientPhotoScan = this.patientPhotoScansRepo.save(patientPhotoScans);
        return this.getPatientPhotoScansDtoMapper.apply(savedPatientPhotoScan);
    }

    @Override
    public String getPatientProfilePhotoByPatientID(String patientID) {
        try{
            GetPatientPhotoScansDto patientPhotoScansDto = getPatientPhotoScansByPatientID(patientID);
            return patientPhotoScansDto.getProfilePhoto().stream().findFirst().orElse(null);
        }
        catch (ResourceNotFoundException ex){
            return null;
        }
    }
}
