package com.pluralsight.controller;

import com.pluralsight.entity.Clinic;
import com.pluralsight.enums.Speciality;
import com.pluralsight.exception.ClinicNotFoundException;
//import com.pluralsight.projection.DoctorProjection;
import com.pluralsight.repository.ClinicRepository;
import com.pluralsight.service.ClinicService;
import com.pluralsight.service.UserService;
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
    private UserService userService;
    private ClinicRepository clinicRepository;

    private static final String CLINIC ="clinic";
    private static final String ADD_PATH = "/"+ CLINIC +"/add";
    //TODO use just 1 Obj by ID
    private static final String BY_ID_UPDATE_PATH = "/"+ CLINIC +"/{clinicId}";
    private static final String BY_ID_PATH = "/"+ CLINIC +"/{id}";

   /* @GetMapping("/clinicProjection")
    public List<DoctorProjection> getAllClinics() {
        List<DoctorProjection> clinicsWithLimitedDoctors = clinicRepository.findAllDoctors();

        // Procesa la lista de clínicas y médicos según sea necesario.

        return clinicsWithLimitedDoctors;
    }*/
    @GetMapping(CLINIC)
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

    @GetMapping(BY_ID_PATH)
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
        return ResponseEntity.status(HttpStatus.CREATED).body(clinic);
    }

   @DeleteMapping(BY_ID_PATH)
    public ResponseEntity<String> deteleClinic(@PathVariable("id") long id) {
        try {
            this.clinicService.deleteClinic(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            String message = "No se pueden borrar clinics si estas contienen appointment";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(BY_ID_UPDATE_PATH)
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long clinicId, @RequestBody Clinic clinic) {
        Clinic updatedClinic = clinicService.updateClinic(clinicId, clinic);
        return ResponseEntity.ok(updatedClinic);
    }

    @GetMapping("/clinicsByUser/{userId}")
    public List<Clinic> getClinicsForCurrentUser(@PathVariable Long userId) {
        return clinicService.getClinicsForCurrentUser(userId);
    }

}