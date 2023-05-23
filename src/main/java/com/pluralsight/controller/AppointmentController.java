package com.pluralsight.controller;

import java.util.List;

import com.pluralsight.exception.AppointmentNotFoundException;
import com.pluralsight.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.pluralsight.service.AppointmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AppointmentController {
    private AppointmentService appointmentService;

    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> getMyAppointments(Authentication authentication) {
        Long personId = extractPersonIdFromAuthentication(authentication);
        List<Appointment> userAppointments = appointmentService.listAppointmentsByPersonId(personId);
        return ResponseEntity.ok(userAppointments);
    }

    private Long extractPersonIdFromAuthentication(Authentication authentication) {
        // Extrae el personId de la autenticación.
        // TODO 2 opciones:
        //  1- hallar el personId por medio del username y asi revolver los Appointments
        //  2- implementar getAppointmentsByUserman()
        //authentication.getPrincipal()
        //Long personIdLoggedPerson = authentication.getPrincipal().;

      /*  UserDetails userDetails = (UserDetails) authentication.g
        String personId = userDetails.getUsername();
       if(personId != null) {
           System.out.println(personId);
       }*/
        return 2L;
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<Appointment> getAppoitment(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Appointment>(this.appointmentService.getAppointmentById(id),
                HttpStatus.OK);
        } catch (AppointmentNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not Found");
        }
    }

    @PostMapping("/appointment/add")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        try {
            appointmentService.createAppointment(appointment);
            return ResponseEntity.ok("Cita médica creada exitosamente.");
        } catch (PersonNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ClinicNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/appointment/{id}")
    public ResponseEntity<Clinic> deteleAppointment(@PathVariable("id") long id) {
        this.appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

}