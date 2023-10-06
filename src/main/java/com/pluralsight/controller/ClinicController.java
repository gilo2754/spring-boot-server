package com.pluralsight.controller;

import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.enums.Speciality;
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
    //TODO use just 1 Obj by ID
    private static final String OBJ_BY_ID_UPDATE_PATH = "/"+ OBJ +"/{clinicId}";
    private static final String OBJ_BY_ID_PATH = "/"+ OBJ +"/{id}";


    @GetMapping("/clinic/{clinic_id}/doctors")
    public List<User> getDoctorsByClinicId(@PathVariable Long clinic_id) {
        return clinicService.getDoctorsByClinicId(clinic_id);
    }

    @GetMapping(OBJ)
    public ResponseEntity<List<Clinic>> getClinicsBySpecialty(@RequestParam(name= "Speciality", required = false) Speciality speciality) {
//        public ResponseEntity<List<Clinic>> getClinicsBySpecialty(@RequestParam(value= "speciality", required = false) String speciality) {

           if (speciality != null )
            {
            // Return list of clinics filtered by specialty
            List<Clinic> clinicsBySpeciality = clinicService.getClinicsBySpeciality(speciality);
            if (clinicsBySpeciality.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No clinics found for speciality: " );//+ speciality);
            }
            return new ResponseEntity<>(clinicsBySpeciality, HttpStatus.OK);
        } else {
            // Return list of all clinics
            List<Clinic> allClinics = this.clinicService.listClinics();
            return new ResponseEntity<List<Clinic>>(allClinics, HttpStatus.OK);
        }

    }

    @GetMapping(OBJ_BY_ID_PATH)
    public ResponseEntity<Clinic> getClinic(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Clinic>(this.clinicService.getClinicById(id),
                HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic Not Found");
        }
    }

    @PostMapping(ADD_PATH)
    public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) {
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
        Clinic updatedClinic = clinicService.updateClinic(clinicId, clinic);
        return ResponseEntity.ok(updatedClinic);
    }

}