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

    @PostMapping("/createPreviousDentalHistoryDetails/{rebootID}")
    public ResponseEntity<PatientPreviousDentalHistory> createPreviousDentalHistoryDetails(
            @RequestBody PatientPreviousDentalHistory patientPreviousDentalHistoryDetails,
            @PathVariable int rebootID
    ) {
        PatientPreviousDentalHistory savedPatientPreviousDentalHistoryDetails =
                this.patientDentalDetailsService.createPreviousDentalHistoryDetails(rebootID, patientPreviousDentalHistoryDetails);
        return new ResponseEntity<>(savedPatientPreviousDentalHistoryDetails, HttpStatus.OK);
    }

    @PostMapping("/createPatientTreatmentGoal/{rebootID}")
    public ResponseEntity<PatientTreatmentGoal> createPatientTreatmentGoal(
            @RequestBody PatientTreatmentGoal patientTreatmentGoal,
            @PathVariable int rebootID
    ) {
        PatientTreatmentGoal savedPatientTreatmentGoal =
                this.patientDentalDetailsService.createPatientTreatmentGoal(rebootID, patientTreatmentGoal);
        return new ResponseEntity<>(savedPatientTreatmentGoal, HttpStatus.OK);
    }

    @PostMapping("/createPatientDentalDetail/{rebootID}")
    public ResponseEntity<PatientDentalDetail> createPatientDentalDetail(
            @RequestBody PatientDentalDetail patientDentalDetail,
            @PathVariable int rebootID
    ) throws BadRequestException {
        PatientDentalDetail savedPatientDentalDetail =
                this.patientDentalDetailsService.createPatientDentalDetail(rebootID, patientDentalDetail);
        return new ResponseEntity<>(savedPatientDentalDetail, HttpStatus.OK);
    }

    @PutMapping("/updatePreviousDentalHistoryDetails/{rebootID}")
    public ResponseEntity<PatientPreviousDentalHistory> updatePreviousDentalHistoryDetails(
            @RequestBody PatientPreviousDentalHistory patientPreviousDentalHistoryDetails,
            @PathVariable int rebootID
    ) {
        PatientPreviousDentalHistory savedPatientPreviousDentalHistoryDetails =
                this.patientDentalDetailsService.updatePreviousDentalHistoryDetails(rebootID, patientPreviousDentalHistoryDetails);
        return new ResponseEntity<>(savedPatientPreviousDentalHistoryDetails, HttpStatus.OK);
    }

    @PutMapping("/updatePatientTreatmentGoal/{rebootID}")
    public ResponseEntity<PatientTreatmentGoal> updatePatientTreatmentGoal(
            @RequestBody PatientTreatmentGoal patientTreatmentGoal,
            @PathVariable int rebootID
    ) {
        PatientTreatmentGoal savedPatientTreatmentGoal =
                this.patientDentalDetailsService.updatePatientTreatmentGoal(rebootID, patientTreatmentGoal);
        return new ResponseEntity<>(savedPatientTreatmentGoal, HttpStatus.OK);
    }

    @PutMapping("/updatePatientDentalDetail/{rebootID}")
    public ResponseEntity<PatientDentalDetail> updatePatientDentalDetail(
            @RequestBody PatientDentalDetail patientDentalDetail,
            @PathVariable int rebootID
    ) throws BadRequestException {
        PatientDentalDetail savedPatientDentalDetail =
                this.patientDentalDetailsService.updatePatientDentalDetail(rebootID, patientDentalDetail);
        return new ResponseEntity<>(savedPatientDentalDetail, HttpStatus.OK);
    }

    @GetMapping("/getPreviousDentalHistoryDetailsByPatientID/{patientID}/{rebootID}")
    public ResponseEntity<Object> getPreviousDentalHistoryDetailsByPatientID(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        Object savedPatientPreviousDentalHistoryDetails =
                this.patientDentalDetailsService.getPreviousDentalHistoryDetailsByPatientID(patientID, rebootID);
        return new ResponseEntity<>(savedPatientPreviousDentalHistoryDetails, HttpStatus.OK);
    }

    @GetMapping("/getPatientTreatmentGoalByPatientID/{patientID}/{rebootID}")
    public ResponseEntity<Object> getPatientTreatmentGoalByPatientID(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        Object savedPatientTreatmentGoal =
                this.patientDentalDetailsService.getPatientTreatmentGoalByPatientID(patientID, rebootID);
        return new ResponseEntity<>(savedPatientTreatmentGoal, HttpStatus.OK);
    }

    @GetMapping("/getPatientDentalDetailByPatientID/{patientID}/{rebootID}")
    public ResponseEntity<Object> getPatientDentalDetailByPatientID(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        Object savedPatientDentalDetail =
                this.patientDentalDetailsService.getPatientDentalDetailByPatientID(patientID, rebootID);
        return new ResponseEntity<>(savedPatientDentalDetail, HttpStatus.OK);
    }
}
