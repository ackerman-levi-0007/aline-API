package com.aline.aline.dao.Impl;

import com.aline.aline.customMapper.TreatmentProgressMapper;
import com.aline.aline.dao.ITreatmentProgressUpdateDao;
import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;
import com.aline.aline.repositories.TreatmentProgressUpdateRepo;
import com.aline.aline.utilities.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TreatmentProgressUpdateDao implements ITreatmentProgressUpdateDao {

    private final TreatmentProgressUpdateRepo treatmentProgressUpdateRepo;

    @Override
    public void createTreatmentProgress(TreatmentProgressUpdate treatmentProgressUpdate) {
        this.treatmentProgressUpdateRepo.save(treatmentProgressUpdate);
    }

    @Override
    public void updateTreatmentProgress(TreatmentProgressUpdate treatmentProgressUpdate) {
        TreatmentProgressUpdate savedProgress = getTreatmentProgressByID(treatmentProgressUpdate.getId().toString());

        savedProgress.setProgress(treatmentProgressUpdate.getProgress());
        savedProgress.setVisitType(treatmentProgressUpdate.getVisitType());
        savedProgress.setAlignerTracking(treatmentProgressUpdate.getAlignerTracking());
        savedProgress.setNotes(treatmentProgressUpdate.getNotes());
        savedProgress.setPhotos(
                CommonUtils.getUpdateListForS3Images(savedProgress.getPhotos(), treatmentProgressUpdate.getPhotos())
        );

        this.treatmentProgressUpdateRepo.save(savedProgress);
    }

    @Override
    public List<TreatmentProgressDto> getAllTreatmentProgress(String patientID) {
        List<TreatmentProgressUpdate> treatmentProgressUpdateList =
                this.treatmentProgressUpdateRepo.findByPatientID(patientID);

        return treatmentProgressUpdateList.stream().map(
                x -> TreatmentProgressMapper.mapper(x)
        ).toList();
    }

    @Override
    public TreatmentProgressDto getTreatmentProgress(String id) {
        TreatmentProgressUpdate treatmentProgressUpdate = getTreatmentProgressByID(id);
        return TreatmentProgressMapper.mapper(treatmentProgressUpdate);
    }

    @Override
    public TreatmentProgressDto getTreatmentProgress(String patientID, int slug) {
        TreatmentProgressUpdate treatmentProgressUpdate = this.treatmentProgressUpdateRepo.findByPatientIDAndSlug(patientID, slug)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Treatment Progress","patientID",patientID
        ));

        return TreatmentProgressMapper.mapper(treatmentProgressUpdate);
    }

    @Override
    public void deleteTreatmentProgress(String id) {
        TreatmentProgressUpdate treatmentProgressUpdate = getTreatmentProgressByID(id);
        this.treatmentProgressUpdateRepo.delete(treatmentProgressUpdate);
    }

    /*****************************************************************************************
                                            Helpers
     *****************************************************************************************/


    private TreatmentProgressUpdate getTreatmentProgressByID(String id){
        return this.treatmentProgressUpdateRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Treatment Progress","id",id
                ));
    }
}
