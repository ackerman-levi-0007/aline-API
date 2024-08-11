package com.aline.aline.services.Impl;

import com.aline.aline.CustomMapper.GetPatientPhotoScansDtoMapper;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.dao.IPatientPhotoScansDao;
import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import com.aline.aline.services.IPatientPhotoScansService;
import com.aline.aline.services.helpers.PatientHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PatientPhotoScansService implements IPatientPhotoScansService {

    private final IPatientPhotoScansDao patientPhotoScansDao;
    private final PatientHelperService patientHelperService;
    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;
    private final GetPatientPhotoScansDtoMapper getPatientPhotoScansDtoMapper;

    @Override
    public GetPatientPhotoScansDto updatePatientPhotoScans(PatientPhotoScans patientPhotoScans) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientPhotoScans.getPatientID());
        return this.patientPhotoScansDao.updatePatientPhotoScans(patientPhotoScans);
    }

    @Override
    public Object getPatientPhotoScansByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        try{
            return this.patientPhotoScansDao.getPatientPhotoScansByPatientID(patientID);
        }
        catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }
    }

    @Override
    public GetPatientPhotoScansDto savePatientPhotoScans(PatientPhotoScans patientPhotoScans) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientPhotoScans.getPatientID());
        PatientPhotoScans savedPatientPhotoScans = this.patientPhotoScansDao.savePatientPhotoScans(patientPhotoScans);
        this.patientDentalDetailsMappingDao.updatePatientPhotoScansIDForPatientID(savedPatientPhotoScans.getPatientID(), savedPatientPhotoScans.getId().toString());
        return this.getPatientPhotoScansDtoMapper.apply(savedPatientPhotoScans);
    }
}
