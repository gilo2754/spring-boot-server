package com.pluralsight.service;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.enums.AppointmentStatus;
import com.pluralsight.enums.Role;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.exception.UserNotFoundException;
import com.pluralsight.repository.AppointmentRepository;
import com.pluralsight.repository.ClinicRepository;
import com.pluralsight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public List<Appointment> listAppointments() {
            return (List<Appointment>) this.appointmentRepository.findAll();
        }

    @Transactional
    public Appointment createAppointment(Appointment appointment){ //  throws PersonNotFoundException, ClinicNotFoundException {

        Optional<User> patientOptional = userRepository.findById(appointment.getPatient().getUser_id());
        if (patientOptional.isEmpty()) {
            throw new UserNotFoundException("El paciente con siguiente ID no encontrado: " + appointment.getPatient().getUser_id());
        }

        Optional<User> doctorOptional = userRepository.findById(appointment.getDoctor().getUser_id());
        if (doctorOptional.isEmpty()) {
         throw new UserNotFoundException("El doctor con siguiente ID no encontrado: " + appointment.getDoctor().getUser_id());
     }

     Optional<Clinic> clinicOptional = clinicRepository.findById(appointment.getClinic().getClinic_id());
     if (clinicOptional.isEmpty()) {
         throw new ClinicNotFoundException("La clínica seleccionada no existe.");
     }

     return appointmentRepository.save(appointment);
 }
 /*
    @Transactional
    public Appointment createAppointment(Appointment appointment) {
        Optional<Person> optionalPerson = personService.getPersonById(personId);
        int maxAppointmentsPerSpecialty = 2; // Define your maximum appointments per specialty limit


        // Check if the person already has an appointment in the same specialty
        boolean hasAppointmentInSpecialty = optionalPerson.getAppointments().stream()
                .anyMatch(appointment -> appointment.getSpecialty() == specialty);

        // Check if the person has reached the maximum appointments for the same specialty
        boolean hasReachedMaxAppointments = optionalPerson.getAppointments().stream()
                .filter(appointment -> appointment.getSpecialty() == specialty)
                .count() >= maxAppointmentsPerSpecialty;

        if (hasAppointmentInSpecialty) {
            throw new IllegalStateException("Person already has an appointment in the same specialty.");
        }

        if (hasReachedMaxAppointments) {
            throw new IllegalStateException("Person has reached the maximum appointments for the same specialty.");
        }

        // Create the new appointment
        Appointment appointment = new Appointment();
        appointment.setPerson(optionalPerson);
        appointment.setSpecialty(specialty);

        // Save the appointment
        appointment = appointmentRepository.save(appointment);

        // Update the person's appointment list
        optionalPerson.getAppointments().add(appointment);
        personService.savePerson(optionalPerson);

        return ;
    }
*/


    @Transactional
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteAppointment(long id) {
        appointmentRepository.deleteById(id);
    }

    @Override public List<Appointment> listAppointmentsByPersonId(Long personId) {
        Optional<User> personOptional = userRepository.findById(personId);
        if (personOptional.isEmpty()) {
            return Collections.emptyList();
        }

        User user = personOptional.get();
        if (Role.DOCTOR.equals(user.getRole())) {
            return appointmentRepository.findByDoctor(user);
        } else if (Role.PATIENT.equals(user.getRole())) {
            return appointmentRepository.findByPatient(user);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Appointment> findByClinic(Long clinicId) {
        Optional<Clinic> clinicOptional = clinicRepository.findById(clinicId);
        if (clinicOptional.isEmpty()) {
            return Collections.emptyList();
        }

        Clinic clinic = clinicOptional.get();
        return appointmentRepository.findByClinic(clinic);
    }

    @Override
    public Appointment reserveAppointment(Long appointmentId, Long patientId) throws Exception {
        // Buscar la cita por su ID
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isEmpty()) {
            throw new Exception("Cita no encontrada");
        }

        Appointment appointment = optionalAppointment.get();

        // Verificar si la cita está disponible
        if (appointment.getAppointment_status() != AppointmentStatus.PENDING) {
            throw new Exception("La cita no está disponible para reserva");
        }

        // Asignar al paciente a la cita
        // Aquí debes tener una referencia al paciente por su ID y asignarlo a la cita
        // Por ejemplo, puedes buscar al paciente en la base de datos
        // y luego asignarlo a la cita

        // Actualizar el estado de la cita a reservada
        appointment.setAppointment_status(AppointmentStatus.RESERVED);
        appointmentRepository.save(appointment);

        return appointment;
    }


}
