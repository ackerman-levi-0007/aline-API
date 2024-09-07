package com.aline.aline.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/patientCommentController")
@Tag(name = "PatientCommentController", description = "This API provides the capability to search and modify comments for the user")
@CrossOrigin("*")
public class PatientCommentController {

}
