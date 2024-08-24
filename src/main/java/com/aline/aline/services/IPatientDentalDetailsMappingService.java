package com.aline.aline.services;

import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientDentalDetailsMappingService {
    List<Integer> getAllRebootIds(String patientID);
    PatientTreatmentPlanMapping getPlanMapping(String patientID, int rebootID);
}
