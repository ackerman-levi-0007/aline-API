package com.aline.aline.repositories;

import com.aline.aline.entities.TreatmentProgressUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentProgressUpdateRepo extends MongoRepository<TreatmentProgressUpdate, String> {
    Optional<TreatmentProgressUpdate> findByPatientIDAndSlug(String patientID, int slug);
    List<TreatmentProgressUpdate> findByPatientIDOrderByCreatedOnDesc(String patientID);
}
