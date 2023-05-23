package com.pluralsight.service;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.Person;
import com.pluralsight.enums.Speciality;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.exception.PersonNotFoundException;
import com.pluralsight.repository.AppointmentRepository;
import com.pluralsight.repository.ClinicRepository;
import com.pluralsight.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private PersonService personService;

    @Transactional
    public List<Appointment> listAppointments() {
            return (List<Appointment>) this.appointmentRepository.findAll();
        }

    @Transactional
    public Appointment createAppointment(Appointment appointment){ //  throws PersonNotFoundException, ClinicNotFoundException {

        Optional<Person> patientOptional = personRepository.findById(appointment.getPatient().getPerson_id());
        if (patientOptional.isEmpty()) {
            throw new PersonNotFoundException("El paciente seleccionado no existe.");
        }

        Optional<Person> doctorOptional = personRepository.findById(appointment.getDoctor().getPerson_id());
        if (doctorOptional.isEmpty()) {
         throw new PersonNotFoundException("El doctor seleccionado no existe.");
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


}
