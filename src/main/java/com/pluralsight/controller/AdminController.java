package com.pluralsight.controller;

import com.pluralsight.entity.User;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1")
@AllArgsConstructor
public class AdminController {
    private PatientService patientService;
   // private DoctorService doctorService;
//TODO: que pueden hacer solo los ADMINS?

}