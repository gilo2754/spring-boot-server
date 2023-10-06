package com.pluralsight.controller;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.exception.AppointmentNotAvailableException;
import com.pluralsight.exception.AppointmentNotFoundException;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.exception.UserNotFoundException;
import com.pluralsight.service.AppointmentService;
import com.pluralsight.service.ClinicService;
import com.pluralsight.service.EmailService;
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
@RequestMapping("/api/v1/appointment")
@AllArgsConstructor
public class AppointmentController {
    private AppointmentService appointmentService;
    private UserService userService;
    private ClinicService clinicService;
    private EmailService emailService;

    //TODO: show just the appointments from the actual user
    @GetMapping
    public ResponseEntity<List<Appointment>> getMyAppointments(){//Authentication authentication) {
        //Long personId = extractPersonIdFromAuthentication(authentication);
        //List<Appointment> userAppointments = appointmentService.listAppointmentsByPersonId(personId);
        List<Appointment> userAppointments =  this.appointmentService.listAppointments();
        return ResponseEntity.ok(userAppointments);
    }

    @PostMapping("/reserve") //FIXME
    public ResponseEntity<String> reserveAppointment(@RequestBody long appointmentId, @RequestBody long patientId) {
        try {
            // Lógica para verificar la disponibilidad y reservar la cita
            appointmentService.reserveAppointment(appointmentId, patientId);
            return ResponseEntity.ok("Cita reservada con éxito");
        } catch (AppointmentNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cita no está disponible");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * To reserve appointments there is the endpoint /reserve
     *
     * @param updatedAppointment
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateAppointment(@RequestBody Appointment updatedAppointment) {
        // Check if the appointmentId is present in the updatedAppointment
        Long appointmentId = updatedAppointment.getAppointment_id();

        if (appointmentId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment ID is required in the request body");
        }

        // Try to fetch the appointment by ID
        Appointment existingAppointment = appointmentService.getAppointmentById(appointmentId);

        if (existingAppointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }

        // Check if the clinic exists
        Clinic clinic = clinicService.getClinicById(updatedAppointment.getClinic().getClinic_id());
        if (clinic == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic not found");
        }

        // Check if the doctor exists
        Optional doctor = userService.getPersonById(updatedAppointment.getDoctor().getUser_id());
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        // Perform validation and update logic
        Appointment updated = appointmentService.updateAppointment(updatedAppointment);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update appointment");
        }

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppoitment(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Appointment>(this.appointmentService.getAppointmentById(id),
                HttpStatus.OK);
        } catch (AppointmentNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not Found");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        try {
            appointmentService.createAppointment(appointment);
            Clinic clinicDetails =
            clinicService.getClinicById(appointment.getClinic().getClinic_id());
            Optional<User> doctorDetails = userService.getPersonById(appointment.getDoctor().getUser_id());
            Optional<User> patientDetails = userService.getPersonById(appointment.getPatient().getUser_id());

            //var patientDetails = userService.getPersonById(appointment.getPatient().getUser_id());
            // Envía un correo electrónico después de crear la cita médica
            String destinatario = "gilo2754@gmail.com";
            String asunto = "Nueva cita médica creada para";
            String doctorFirstName = doctorDetails.get().getFirstName();
            String patientirstName = patientDetails.get().getFirstName();
            String clinicName = clinicDetails.getClinic_name();

            String mensaje = "Se ha creado una nueva cita médica para^"+ patientirstName + ". Detalles:\n\n" +
                    "Fecha y hora: " + appointment.getStartTime() + "\n" +
                    "Médico: " + doctorFirstName + "\n" +
                    "Clinica: " + clinicName + "\n" +
                    "Paciente: " + patientirstName;
            // TODO completar con detalles reales de la cita
            // TODO: mover la la creacion del E-Mail a otra clase?
            emailService.enviarCorreo(destinatario, asunto, mensaje);

            return ResponseEntity.ok("Cita médica creada exitosamente para "+ patientirstName);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ClinicNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Clinic> deteleAppointment(@PathVariable("id") long id) {
        this.appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clinic/{clinicId}")
//    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> getAppointmentsByClinic(@PathVariable Long clinicId) {
        // TODO Verificar si el usuario tiene el rol "DOCTOR"
      /*  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasDoctorRole = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DOCTOR"));

        if (!hasDoctorRole) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permisos para acceder a este recurso.");
        }
*/
        List<Appointment> appointments = appointmentService.findByClinic(clinicId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron citas para la clínica con ID: " + clinicId);
        } else {
            return ResponseEntity.ok(appointments);
        }
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
}