package com.aline.aline.dao;


import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressListDto;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressUpdateDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentProgressUpdateDao {
    void createTreatmentProgress(TreatmentProgressUpdate treatmentProgressUpdate, boolean clickable);
    void updateTreatmentProgress(TreatmentProgressUpdateDto treatmentProgressUpdate);
    List<TreatmentProgressListDto> getAllTreatmentProgress(String patientID);
    TreatmentProgressDto getTreatmentProgress(String id);
    TreatmentProgressDto getTreatmentProgress(String patientID, int slug);
    void deleteTreatmentProgress(String id);
}
