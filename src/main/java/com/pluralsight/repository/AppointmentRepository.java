package com.pluralsight.repository;

import com.pluralsight.entity.Appointment;
import com.pluralsight.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

}
