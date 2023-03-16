package com.pluralsight.controller;

import com.pluralsight.entity.Doctor;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/doctor/api/v1")
@AllArgsConstructor
public class DoctorController {
    private DoctorService doctorService;

    @GetMapping("/doctors")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Doctor>> getUsers() {
        List<Doctor> list = this.doctorService.listDoctors();
        return new ResponseEntity<List<Doctor>>(list, HttpStatus.OK);
    }

    @PostMapping("/doctor/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Doctor> createUser(@RequestBody Doctor doctor) {
        this.doctorService.createDoctor(doctor);
        System.out.println("New doctor was created");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/doctor/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Doctor> deteleDoctor(@PathVariable("id") long id) {
        this.doctorService.deleteDoctor(id);
        System.out.println("Doctor with id: " + id + " was deleted");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Doctor>(this.doctorService.findDoctor(id), HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor Not Found");
        }
    }

}