package com.pluralsight.controller;

import com.pluralsight.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1")
@AllArgsConstructor
public class AdminController {
    private PatientService patientService;
   // private DoctorService doctorService;
//TODO: que pueden hacer solo los ADMINS?

}