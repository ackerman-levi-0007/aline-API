package com.aline.aline.controllers;
import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import com.aline.aline.services.IPatientDentalDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/patientDentalDetails")
@Tag(name = "PatientDentalDetailsController", description = "This API provides the capability to search and modify dental details of patients")
@CrossOrigin("*")
public class PatientDentalDetailsController {
    private final IPatientDentalDetailsService patientDentalDetailsService;

    @PostMapping("/createPreviousDentalHistoryDetails")
    public ResponseEntity<PatientPreviousDentalHistory> createPreviousDentalHistoryDetails(
            @RequestBody PatientPreviousDentalHistory patientPreviousDentalHistoryDetails
    ) throws BadRequestException {
        PatientPreviousDentalHistory savedPatientPreviousDentalHistoryDetails =
                this.patientDentalDetailsService.createPreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails);
        return new ResponseEntity<>(savedPatientPreviousDentalHistoryDetails, HttpStatus.OK);
    }

    @PostMapping("/createPatientTreatmentGoal")
    public ResponseEntity<PatientTreatmentGoal> createPatientTreatmentGoal(
            @RequestBody PatientTreatmentGoal patientTreatmentGoal
    ) throws BadRequestException {
        PatientTreatmentGoal savedPatientTreatmentGoal =
                this.patientDentalDetailsService.createPatientTreatmentGoal(patientTreatmentGoal);
        return new ResponseEntity<>(savedPatientTreatmentGoal, HttpStatus.OK);
    }

    @PostMapping("/createPatientDentalDetail")
    public ResponseEntity<PatientDentalDetail> createPatientDentalDetail(
            @RequestBody PatientDentalDetail patientDentalDetail
    ) throws BadRequestException {
        PatientDentalDetail savedPatientDentalDetail =
                this.patientDentalDetailsService.createPatientDentalDetail(patientDentalDetail);
        return new ResponseEntity<>(savedPatientDentalDetail, HttpStatus.OK);
    }

    @PutMapping("/updatePreviousDentalHistoryDetails")
    public ResponseEntity<PatientPreviousDentalHistory> updatePreviousDentalHistoryDetails(
            @RequestBody PatientPreviousDentalHistory patientPreviousDentalHistoryDetails
    ) throws BadRequestException {
        PatientPreviousDentalHistory savedPatientPreviousDentalHistoryDetails =
                this.patientDentalDetailsService.updatePreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails);
        return new ResponseEntity<>(savedPatientPreviousDentalHistoryDetails, HttpStatus.OK);
    }

    @PutMapping("/updatePatientTreatmentGoal")
    public ResponseEntity<PatientTreatmentGoal> updatePatientTreatmentGoal(
            @RequestBody PatientTreatmentGoal patientTreatmentGoal
    ) throws BadRequestException {
        PatientTreatmentGoal savedPatientTreatmentGoal =
                this.patientDentalDetailsService.updatePatientTreatmentGoal(patientTreatmentGoal);
        return new ResponseEntity<>(savedPatientTreatmentGoal, HttpStatus.OK);
    }

    @PutMapping("/updatePatientDentalDetail")
    public ResponseEntity<PatientDentalDetail> updatePatientDentalDetail(
            @RequestBody PatientDentalDetail patientDentalDetail
    ) throws BadRequestException {
        PatientDentalDetail savedPatientDentalDetail =
                this.patientDentalDetailsService.updatePatientDentalDetail(patientDentalDetail);
        return new ResponseEntity<>(savedPatientDentalDetail, HttpStatus.OK);
    }

    @GetMapping("/getPreviousDentalHistoryDetailsByPatientID/{patientID}")
    public ResponseEntity<Object> getPreviousDentalHistoryDetailsByPatientID(
            @PathVariable String patientID
    ) throws BadRequestException {
        Object savedPatientPreviousDentalHistoryDetails =
                this.patientDentalDetailsService.getPreviousDentalHistoryDetailsByPatientID(patientID);
        return new ResponseEntity<>(savedPatientPreviousDentalHistoryDetails, HttpStatus.OK);
    }

    @GetMapping("/getPatientTreatmentGoalByPatientID/{patientID}")
    public ResponseEntity<Object> getPatientTreatmentGoalByPatientID(
            @PathVariable String patientID
    ) throws BadRequestException {
        Object savedPatientTreatmentGoal =
                this.patientDentalDetailsService.getPatientTreatmentGoalByPatientID(patientID);
        return new ResponseEntity<>(savedPatientTreatmentGoal, HttpStatus.OK);
    }

    @GetMapping("/getPatientDentalDetailByPatientID/{patientID}")
    public ResponseEntity<Object> getPatientDentalDetailByPatientID(
            @PathVariable String patientID
    ) throws BadRequestException {
        Object savedPatientDentalDetail =
                this.patientDentalDetailsService.getPatientDentalDetailByPatientID(patientID );
        return new ResponseEntity<>(savedPatientDentalDetail, HttpStatus.OK);
    }

}
