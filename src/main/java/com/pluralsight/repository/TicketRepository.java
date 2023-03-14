package com.pluralsight.repository;

import com.pluralsight.entity.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Appointment, Long> {
}
