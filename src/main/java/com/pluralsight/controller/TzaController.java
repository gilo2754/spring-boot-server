package com.pluralsight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.Appointment;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.ClinicService;
import com.pluralsight.service.AppointmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TzaController {
    private ClinicService clinicService;

    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllTickets() {
        List<Appointment> list = this.appointmentService.listAppointments();
        return new ResponseEntity<List<Appointment>>(list, HttpStatus.OK);
    }

    @GetMapping("/clinics")
    public ResponseEntity<List<Clinic>> getAllApplications() {
        List<Clinic> list = this.clinicService.listClinics();
        return new ResponseEntity<List<Clinic>>(list, HttpStatus.OK);
    }

    @GetMapping("/clinic/{id}")
    public ResponseEntity<Clinic> getApplication(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Clinic>(this.clinicService.findClinic(id),
                HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic Not Found");
        }
    }

    @PostMapping("/clinic/add")
    public ResponseEntity<Clinic> createApplication(@RequestBody Clinic clinic) {
        // public Application createApplication(@RequestBody Application application) {
        this.clinicService.addClinic(clinic);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/clinic/{id}")
    public ResponseEntity<Clinic> deteleClinic(@PathVariable("id") long id) {
        this.clinicService.deleteClinic(id);
        return ResponseEntity.noContent().build();
    }

}