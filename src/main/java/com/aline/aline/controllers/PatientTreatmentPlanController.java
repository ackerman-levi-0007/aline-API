package com.aline.aline.controllers;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
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
@RequestMapping("/api/v1/aline/treatmentPlan")
@Tag(name = "PatientTreatmentPlanController", description = "This API provides the capability to search and modify treatment plan details of patients")
@CrossOrigin("*")
public class PatientTreatmentPlanController {

    private final IPatientTreatmentPlanService patientTreatmentPlanService;

    @PostMapping("/createPlan/{patientID}/{rebootID}")
    public ResponseEntity<PatientTreatmentPlanDto> createPlan(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlanDto
    ){
        PatientTreatmentPlanDto savedPatientTreatmentPlan = this.patientTreatmentPlanService.createTreatmentPlan(patientTreatmentPlanDto);
        return new ResponseEntity<>(savedPatientTreatmentPlan, HttpStatus.OK);
    }

    @PostMapping("/saveDraft/{patientID}")
    public ResponseEntity<APIResponse> saveDraft(
            @PathVariable String patientID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ){
        this.patientTreatmentPlanService.saveDraftForTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Draft saved successfully !!!", true), HttpStatus.OK);
    }

    @PutMapping("/sendPlanModificationToDoctor/{patientID}/{treatmentPlanID}")
    public ResponseEntity<APIResponse> sendPlanModificationToDoctor(
            @PathVariable String patientID,
            @PathVariable String treatmentPlanID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ) throws BadRequestException {
        this.patientTreatmentPlanService.sendTreatmentPlanModificationToDoctor(patientID, treatmentPlanID, patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Treatment plan shared successfully to doctor !!!", true), HttpStatus.OK);
    }

    @GetMapping("/getPlan/{patientID}/{planID}/{rebootID}")
    public ResponseEntity<PatientTreatmentPlanDto> getPlan(
            @PathVariable String patientID,
            @PathVariable String planID,
            @PathVariable int rebootID,
            @RequestParam TreatmentPlanType planType
     ) throws BadRequestException {
        PatientTreatmentPlanDto patientTreatmentPlan = this.patientTreatmentPlanService
                .getTreatmentPlan(patientID, rebootID, planID, planType);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }

    @GetMapping("/getAllPlanForPatientID/{patientID}")
    public ResponseEntity<List<PatientTreatmentPlanDto>> getAllPlanForPatientID(
            @PathVariable String patientID
    ){
        List<PatientTreatmentPlanDto> patientTreatmentPlans = this.patientTreatmentPlanService.getAllTreatmentPlanForPatientID(patientID);
        return new ResponseEntity<>(patientTreatmentPlans, HttpStatus.OK);
    }

}
