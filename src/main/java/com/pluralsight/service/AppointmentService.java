package com.pluralsight.service;

import com.pluralsight.entity.Appointment;
import com.pluralsight.enums.Speciality;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<Appointment> listAppointments();

    Appointment createAppointment(Appointment appointment);

    /*
 public Appointment createAppointment(Appointment appointment) {

     return appointmentRepository.save(appointment);
 }
 */
    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Appointment appointment);

    void deleteAppointment(long id);
}


