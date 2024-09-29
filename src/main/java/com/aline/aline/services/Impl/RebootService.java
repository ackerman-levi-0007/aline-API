package com.aline.aline.services.Impl;

import com.aline.aline.customMapper.DeepClone;
import com.aline.aline.dao.IDentalDetailsMappingDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.services.IRebootService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebootService implements IRebootService {

    private final IDentalDetailsMappingDao dentalDetailsMappingDao;

    @Override
    public void createReboot(String patientID) {

        List<PatientDentalDetailsMapping> patientDentalDetailsMappingList =
                this.dentalDetailsMappingDao.getPatientDentalDetailsMapping(patientID);

        Optional<PatientDentalDetailsMapping> latestPatientDentalMapping = patientDentalDetailsMappingList.stream()
                .max(Comparator.comparingInt(PatientDentalDetailsMapping::getRebootID));

        PatientDentalDetailsMapping newMapping;

        if(latestPatientDentalMapping.isPresent()){
            newMapping = DeepClone.deepCloneForNewMapping(latestPatientDentalMapping.get());
            newMapping.setRebootID(latestPatientDentalMapping.get().getRebootID() + 1);
        }
        else{
            throw new ResourceNotFoundException("Mapping not found for the patient");
        }

        this.dentalDetailsMappingDao.saveMapping(newMapping);
    }
}
