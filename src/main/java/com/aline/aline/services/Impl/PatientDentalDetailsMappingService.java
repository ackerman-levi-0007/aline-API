package com.aline.aline.services.Impl;

import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import com.aline.aline.services.IPatientDentalDetailsMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDentalDetailsMappingService implements IPatientDentalDetailsMappingService {

    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;

    @Override
    public List<Integer> getAllRebootIds(String patientID) {
        return this.patientDentalDetailsMappingDao.getAllRebootIds(patientID);
    }

    @Override
    public PatientTreatmentPlanMapping getPlanMapping(String patientID, int rebootID) {
        return this.patientDentalDetailsMappingDao.getPlanMapping(patientID, rebootID);
    }
}
