package com.pluralsight.repository;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

     /**
      * Find appointments associated with a specific doctor.
      *
      * @param doctor The doctor associated with the appointments.
      * @return The list of appointments associated with the doctor.
      * Note: The implementation of this method is automatically generated by Spring Data JPA.
      */
    List<Appointment> findByDoctor(User doctor);

    /**
     * Find appointments associated with a specific patient.
     *
     * @param patient The patient associated with the appointments.
     * @return The list of appointments associated with the patient.
     * Note: The implementation of this method is automatically generated by Spring Data JPA.
     */
    List<Appointment> findByPatient(User patient);

    List<Appointment> findByClinic(Clinic clinic);

}

