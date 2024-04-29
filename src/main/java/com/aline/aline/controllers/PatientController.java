package com.aline.aline.controllers;

import com.aline.aline.entities.Patient;
import com.aline.aline.enums.PatientStatus;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.Patient.UpdateDoctorAllocationDto;
import com.aline.aline.payload.Patient.UpdatePatientDto;
import com.aline.aline.payload.Patient.UpdatePatientStatusDto;
import com.aline.aline.services.IPatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/patient")
@Tag(name = "PatientController", description = "This API provides the capability to search and modify details of patients")
@CrossOrigin("*")
public class PatientController {

    private final IPatientService patientService;

    @PostMapping("/createPatient")
    public ResponseEntity<GetPatientDto> createPatient(
            @RequestBody Patient patient
    ){
        GetPatientDto getPatientDto = this.patientService.createPatient(patient);
        return new ResponseEntity<>(getPatientDto, HttpStatus.OK);
    }

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<GetPatientDto>> getAllPatients(){
        List<GetPatientDto> getPatientDtoList = this.patientService.getAllPatients();
        return new ResponseEntity<>(getPatientDtoList, HttpStatus.OK);
    }

    @GetMapping("/getPatientByID/{patientID}")
    public ResponseEntity<GetPatientDto> getPatientByID(
            @PathVariable String patientID
    ){
        GetPatientDto patientDto = this.patientService.getPatientByID(patientID);
        return new ResponseEntity<>(patientDto, HttpStatus.OK);
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<GetPatientDto> updatePatient(
            @RequestBody UpdatePatientDto patient
    ){
        GetPatientDto updatedPatient = this.patientService.updatePatient(patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @GetMapping("/getStatusValuesForPatient")
    public ResponseEntity<List<String>> getStatusValuesForPatient(){
        List<String> patientStatus = List.of(Arrays.toString(PatientStatus.values()));
        return new ResponseEntity<>(patientStatus, HttpStatus.OK);
    }

    @PutMapping("/updatePatientStatus")
    public ResponseEntity<APIResponse> updatePatientStatus(
            @RequestBody UpdatePatientStatusDto patientStatus
    ){
        this.patientService.updatePatientStatus(patientStatus);
        return new ResponseEntity<>(new APIResponse("Patient status updated successfully!!!", true), HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient/{patientID}")
    public ResponseEntity<APIResponse> deletePatient(
            @PathVariable String patientID
    ){
        this.patientService.deletePatient(patientID);
        return new ResponseEntity<>(new APIResponse("Patient deleted successfully!!!", true), HttpStatus.OK);
    }

    @PutMapping("/changeDoctorAllocationForPatient")
    public ResponseEntity<APIResponse> changeDoctorAllocationForPatient(
            @RequestBody UpdateDoctorAllocationDto doctorAllocationDto
    ){
        this.patientService.changeDoctorAllocationForPatient(doctorAllocationDto);
        return new ResponseEntity<>(new APIResponse("Doctor changed successfully for the patient!!!", true), HttpStatus.OK);
    }
}
