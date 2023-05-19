package com.pluralsight.service;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Person;
import com.pluralsight.enums.Speciality;
import com.pluralsight.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PersonService personService;

    @Override
        public List<Appointment> listAppointments() {
            return (List<Appointment>) this.appointmentRepository.findAll();
        }


 public Appointment createAppointment(Appointment appointment) {

     return appointmentRepository.save(appointment);
 }
 /*
    @Override
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


    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(long id) {
        appointmentRepository.deleteById(id);
    }


}
