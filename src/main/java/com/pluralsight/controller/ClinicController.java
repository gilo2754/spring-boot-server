package com.pluralsight.controller;

import com.pluralsight.entity.Clinic;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.ClinicService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    private static final String OBJ ="clinic";
    private static final String ADD_PATH = "/"+ OBJ +"/add";
    private static final String GET_ALL_PATH = "/"+ OBJ +"s";
    //TODO use just 1 Obj by ID
    private static final String OBJ_BY_ID_UPDATE_PATH = "/"+ OBJ +"/{clinicId}";
    private static final String OBJ_BY_ID_PATH = "/"+ OBJ +"/{id}";

    @GetMapping(GET_ALL_PATH)
    public ResponseEntity<List<Clinic>> getAllApplications() {
        List<Clinic> list = this.clinicService.listClinics();
        return new ResponseEntity<List<Clinic>>(list, HttpStatus.OK);
    }

    @GetMapping(OBJ_BY_ID_PATH)
    public ResponseEntity<Clinic> getApplication(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Clinic>(this.clinicService.getClinicById(id),
                HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic Not Found");
        }
    }

//    @PostMapping("/clinic/add")
    @PostMapping(ADD_PATH)
    public ResponseEntity<Clinic> createApplication(@RequestBody Clinic clinic) {
        this.clinicService.createClinic(clinic);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(OBJ_BY_ID_PATH)
    public ResponseEntity<String> deteleClinic(@PathVariable("id") long id) {
        try {
            this.clinicService.deleteClinic(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            String message = "No se pueden borrar clinics si estas contienen appointment";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(OBJ_BY_ID_UPDATE_PATH)
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long clinicId, @RequestBody Clinic clinic) {
        Clinic updatedClinic = clinicService.update(clinicId, clinic);
        return ResponseEntity.ok(updatedClinic);
    }

}