package com.pluralsight.controller;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.exception.AppointmentNotFoundException;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.exception.UserNotFoundException;
import com.pluralsight.service.AppointmentService;
import com.pluralsight.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AppointmentController {
    private AppointmentService appointmentService;
    private UserService userService;

    //TODO: show just the appointments from the actual user
    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> getMyAppointments(){//Authentication authentication) {
        //Long personId = extractPersonIdFromAuthentication(authentication);
        //List<Appointment> userAppointments = appointmentService.listAppointmentsByPersonId(personId);
        List<Appointment> userAppointments =  this.appointmentService.listAppointments();
        return ResponseEntity.ok(userAppointments);
    }

    /**
     * Extracts the personId from the authentication.
     * Finds the personId by username and returns the appointments from this user.
     *
     * @param authentication the authentication object representing the currently authenticated user
     * @return the list of appointments for the authenticated user
     */
    private Long extractPersonIdFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long personIdLoggedPerson = null;
        if (username != null) {
            System.out.println("USERNAME LOGED: " + username);
            Optional<User> optionalPerson = userService.getUserByUsername(username);
            personIdLoggedPerson= optionalPerson.map(User::getUser_id).orElse(null);
        }

        return personIdLoggedPerson;
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

    @PostMapping("/appointment/create")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        try {
            appointmentService.createAppointment(appointment);
            return ResponseEntity.ok("Cita médica creada exitosamente.");
        } catch (UserNotFoundException e) {
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

    @GetMapping("/appointment/clinic/{clinicId}")
    public ResponseEntity<?> getAppointmentsByClinic(@PathVariable Long clinicId) {
        List<Appointment> appointments = appointmentService.findByClinic(clinicId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No appointments found for clinic with ID: " + clinicId);
        } else {
            return ResponseEntity.ok(appointments);
        }
    }




}