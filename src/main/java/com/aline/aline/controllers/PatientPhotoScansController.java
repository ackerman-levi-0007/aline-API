package com.aline.aline.controllers;

import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import com.aline.aline.services.IPatientPhotoScansService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/patientPhotoScans")
@Tag(name = "PatientPhotoScansController", description = "This API provides the capability to search and modify photos and scans details of patients")
@CrossOrigin("*")
public class PatientPhotoScansController {
    private final IPatientPhotoScansService patientPhotoScansService;

    @PutMapping("/updatePatientPhotoScans/{rebootID}")
    public ResponseEntity<GetPatientPhotoScansDto> updatePatientPhotoScans(
            @RequestBody PatientPhotoScans patientPhotoScans,
            @PathVariable int rebootID
    ) {
        GetPatientPhotoScansDto getPatientPhotoScansDto =
                this.patientPhotoScansService.updatePatientPhotoScans(patientPhotoScans, rebootID);
        return new ResponseEntity<>(getPatientPhotoScansDto, HttpStatus.OK);
    }

    @GetMapping("/getPatientPhotoScansByPatientID/{patientID}/{rebootID}")
    public ResponseEntity<Object> getPatientPhotoScansByPatientID(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        Object patientPhotoScans =
                this.patientPhotoScansService.getPatientPhotoScansByPatientID(patientID, rebootID);
        return new ResponseEntity<>(patientPhotoScans, HttpStatus.OK);
    }

    @PostMapping("/savePatientPhotoScans/{rebootID}")
    public ResponseEntity<GetPatientPhotoScansDto> savePatientPhotoScans(
            @RequestBody PatientPhotoScans patientPhotoScans,
            @PathVariable int rebootID
    ) {
        GetPatientPhotoScansDto savedPatientPhotoScans =
                this.patientPhotoScansService.savePatientPhotoScans(patientPhotoScans, rebootID);
        return new ResponseEntity<>(savedPatientPhotoScans, HttpStatus.OK);
    }
}
