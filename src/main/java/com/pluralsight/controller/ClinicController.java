package com.pluralsight.controller;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Clinic;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.AppointmentService;
import com.pluralsight.service.ClinicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ClinicController {
    private ClinicService clinicService;

    @GetMapping("/clinics")
    public ResponseEntity<List<Clinic>> getAllApplications() {
        List<Clinic> list = this.clinicService.listClinics();
        return new ResponseEntity<List<Clinic>>(list, HttpStatus.OK);
    }

    @GetMapping("/clinic/{id}")
    public ResponseEntity<Clinic> getApplication(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Clinic>(this.clinicService.getClinicById(id),
                HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic Not Found");
        }
    }

    @PostMapping("/clinic/add")
    public ResponseEntity<Clinic> createApplication(@RequestBody Clinic clinic) {
        this.clinicService.createClinic(clinic);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clinic/{id}")
    public ResponseEntity<Clinic> deteleClinic(@PathVariable("id") long id) {
        this.clinicService.deleteClinic(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clinic/{clinicId}")
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long clinicId, @RequestBody Clinic clinic) {
        Clinic updatedClinic = clinicService.update(clinicId, clinic);
        return ResponseEntity.ok(updatedClinic);
    }

}