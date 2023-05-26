package com.pluralsight.controller;

import java.util.List;
import java.util.Optional;

import com.pluralsight.entity.Person;
import com.pluralsight.exception.AppointmentNotFoundException;
import com.pluralsight.exception.PersonNotFoundException;
import com.pluralsight.service.PersonService;
import org.hibernate.sql.Template;
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
    private PersonService personService;

    @GetMapping("/appointment")
    public ResponseEntity<List<Appointment>> getMyAppointments(Authentication authentication) {
        Long personId = extractPersonIdFromAuthentication(authentication);
        List<Appointment> userAppointments = appointmentService.listAppointmentsByPersonId(personId);
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
            Optional<Person> optionalPerson = personService.getUserByUsername(username);
            personIdLoggedPerson= optionalPerson.map(Person::getPerson_id).orElse(null);
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