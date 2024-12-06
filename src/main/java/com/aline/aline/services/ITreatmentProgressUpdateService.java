package com.aline.aline.services;

import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITreatmentProgressUpdateService {
    TreatmentProgressDto getTreatmentProgress(String patientID, String id);
    void createTreatmentProgress(String patientID, TreatmentProgressUpdate treatmentProgressUpdate);
    void updateTreatmentProgress(String patientID, TreatmentProgressUpdateDto treatmentProgressUpdate);
    List<TreatmentProgressDto> getAllTreatmentProgress(String patientID);
    void deleteTreatmentProgress(String patientID, String id);
}
