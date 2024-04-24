package com.aline.aline.repositories;

import com.aline.aline.entities.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends MongoRepository<Patient, String> {
}
