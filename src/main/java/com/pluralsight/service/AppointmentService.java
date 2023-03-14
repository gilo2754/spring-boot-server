package com.pluralsight.service;

import com.pluralsight.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<Appointment> listAppointments();

    Appointment createAppointment(Appointment appointment);

    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Appointment appointment);


}


