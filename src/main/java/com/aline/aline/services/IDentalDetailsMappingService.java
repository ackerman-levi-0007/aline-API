package com.aline.aline.services;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDentalDetailsMappingService {
    List<Integer> getAllRebootIds(String patientID);
    PatientTreatmentPlanMapping getPlanMapping(String patientID, int rebootID);
}
