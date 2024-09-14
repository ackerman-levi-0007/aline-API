package com.aline.aline.controllers;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import com.aline.aline.services.IPatientDentalDetailsMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/dentalDetailsMapping")
@Tag(name = "PatientDentalDetailsMappingController", description = "This API provides the capability to search patient dental details mapping")
@CrossOrigin("*")
public class PatientDentalDetailsMappingController {

    private final IPatientDentalDetailsMappingService patientDentalDetailsMappingService;

    @GetMapping("/getAllRebootIds/{patientID}")
    public ResponseEntity<List<Integer>> getAllRebootIds(
            @PathVariable String patientID
    ) {
        List<Integer> rebootIds =
                this.patientDentalDetailsMappingService.getAllRebootIds(patientID);
        return new ResponseEntity<>(rebootIds, HttpStatus.OK);
    }

    @GetMapping("/getPlanMapping/{patientID}/{rebootID}")
    public ResponseEntity<PatientTreatmentPlanMapping> getPlanMapping(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        PatientTreatmentPlanMapping patientTreatmentPlanMapping =
                this.patientDentalDetailsMappingService.getPlanMapping(patientID, rebootID);
        return new ResponseEntity<>(patientTreatmentPlanMapping, HttpStatus.OK);
    }

    //For testing has to remove

    @PostMapping("/addPatientTreatmentPlanID/{patientID}/{rebootID}")
    public ResponseEntity<APIResponse> addPatientTreatmentPlanID(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @RequestBody TreatmentPlanObject treatmentPlanObject
            ) {
        this.patientDentalDetailsMappingService.addPatientTreatmentPlanID(patientID, treatmentPlanObject, rebootID);
        return new ResponseEntity<>(new APIResponse("ok", true), HttpStatus.OK);
    }

    @PostMapping("/addUnsavedDraftTreatmentPlanID/{patientID}/{rebootID}")
    public ResponseEntity<APIResponse> addUnsavedDraftTreatmentPlanID(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @RequestBody TreatmentPlanObject treatmentPlanObject
    ) {
        this.patientDentalDetailsMappingService.addUnsavedDraftTreatmentPlanID(patientID, treatmentPlanObject, rebootID);
        return new ResponseEntity<>(new APIResponse("ok", true), HttpStatus.OK);
    }

    @PostMapping("/addTreatmentPlanToHistory/{patientID}/{rebootID}")
    public ResponseEntity<APIResponse> addTreatmentPlanToHistory(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @RequestBody List<TreatmentPlanObject> treatmentPlanObject
    ) {
        this.patientDentalDetailsMappingService.addTreatmentPlanToHistory(patientID, treatmentPlanObject, rebootID);
        return new ResponseEntity<>(new APIResponse("ok", true), HttpStatus.OK);
    }

    @PostMapping("/moveTreatmentPlanToHistory/{patientID}/{rebootID}")
    public ResponseEntity<APIResponse> moveTreatmentPlanToHistory(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        this.patientDentalDetailsMappingService.moveTreatmentPlanToHistory(patientID, rebootID);
        return new ResponseEntity<>(new APIResponse("ok", true), HttpStatus.OK);
    }
}
