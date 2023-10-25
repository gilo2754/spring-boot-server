package com.pluralsight.service;

import com.pluralsight.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> listAppointments();

    Appointment createAppointment(Appointment appointment);

   Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Appointment appointment);

    void deleteAppointment(long id);

    List<Appointment> listAppointmentsByPersonId(Long personId);

    List<Appointment> findByClinic(Long clinicId);

    Appointment reserveAppointment(Long appointmentId, Long patientId) throws Exception;

    List<Appointment> getAppointmentsByDoctorId(Long doctorId);
}


