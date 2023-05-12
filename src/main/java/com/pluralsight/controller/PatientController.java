package com.pluralsight.controller;

import java.util.List;

import com.pluralsight.entity.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.PatientService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PatientController {
    private PatientService patientService;

    @GetMapping("/patients")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> list = this.patientService.listPatients();
        return new ResponseEntity<List<Patient>>(list, HttpStatus.OK);
    }

    @PostMapping("/patient/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        this.patientService.createPatient(patient);
        System.out.println("New user was created");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Patient> detelePatient(@PathVariable("id") long id) {
        this.patientService.deletePatient(id);
        System.out.println("patient with id: " + id + " was deleted");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{id}")
    // Just roles(@{ApplicationUserRole}) which contains those authorities can access hier
    // @PreAuthorize("hasAuthority('user:read')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Patient>(this.patientService.findPatient(id), HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

}