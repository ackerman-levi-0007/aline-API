package com.aline.aline.controllers;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.services.IPatientTreatmentPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/patientTreatmentPlanController")
@Tag(name = "PatientTreatmentPlanController", description = "This API provides the capability to search and modify treatment plan details of patients")
@CrossOrigin("*")
public class PatientTreatmentPlanController {

    private final IPatientTreatmentPlanService patientTreatmentPlanService;

    @PostMapping("/createTreatmentPlan")
    public ResponseEntity<PatientTreatmentPlan> createTreatmentPlan(
            @RequestHeader String patientID,
            @RequestBody PatientTreatmentPlan patientTreatmentPlan
    ){
        PatientTreatmentPlan savedPatientTreatmentPlan = this.patientTreatmentPlanService.createTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(savedPatientTreatmentPlan, HttpStatus.OK);
    }

    @PostMapping("/saveDraftForTreatmentPlan")
    public ResponseEntity<APIResponse> saveDraftForTreatmentPlan(
            @RequestHeader String patientID,
            @RequestBody PatientTreatmentPlan patientTreatmentPlan
    ){
        PatientTreatmentPlan savedPatientTreatmentPlan = this.patientTreatmentPlanService.saveDraftForTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Draft saved successfully !!!", true), HttpStatus.OK);
    }

    @PutMapping("/sendTreatmentPlanModificationToDoctor")
    public ResponseEntity<APIResponse> sendTreatmentPlanModificationToDoctor(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID,
            @RequestBody PatientTreatmentPlan patientTreatmentPlan
    ) throws BadRequestException {
        this.patientTreatmentPlanService.sendTreatmentPlanModificationToDoctor(patientID, treatmentPlanID, patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Treatment plan shared successfully to doctor !!!", true), HttpStatus.OK);
    }

    @GetMapping("/getTreatmentPlan")
    public ResponseEntity<PatientTreatmentPlan> getTreatmentPlan(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID
    ){
        PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanService.getTreatmentPlan(patientID, treatmentPlanID);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }

    @GetMapping("/getAllTreatmentPlanForPatientID")
    public ResponseEntity<List<PatientTreatmentPlan>> getAllTreatmentPlanForPatientID(
            @RequestHeader String patientID
    ){
        List<PatientTreatmentPlan> patientTreatmentPlans = this.patientTreatmentPlanService.getAllTreatmentPlanForPatientID(patientID);
        return new ResponseEntity<>(patientTreatmentPlans, HttpStatus.OK);
    }

    @GetMapping("/getTreatmentPlanDraft")
    public ResponseEntity<PatientTreatmentPlan> getTreatmentPlanDraft(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID
    ){
        PatientTreatmentPlan patientTreatmentPlanDraft = this.patientTreatmentPlanService.getTreatmentPlanDraft(patientID, treatmentPlanID);
        return new ResponseEntity<>(patientTreatmentPlanDraft, HttpStatus.OK);
    }

    @GetMapping("/getAllHistoricalVersionIDsForTreatmentPlan")
    public ResponseEntity<List<String>> getAllHistoricalVersionIDsForTreatmentPlan(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID
    ){
        List<String> ids = this.patientTreatmentPlanService.getAllHistoricalVersionIDsForTreatmentPlan(patientID, treatmentPlanID);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/getHistoricalTreatmentPlan")
    public ResponseEntity<PatientTreatmentPlan> getHistoricalTreatmentPlan(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID,
            @RequestHeader String treatmentPlanVersionID
    ){
        PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanService.getHistoricalTreatmentPlan(patientID, treatmentPlanID, treatmentPlanVersionID);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }
}
