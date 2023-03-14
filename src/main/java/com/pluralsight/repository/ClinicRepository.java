package com.pluralsight.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pluralsight.entity.Clinic;
import org.springframework.stereotype.Repository;

@Repository

public interface ClinicRepository extends CrudRepository<Clinic, Long> {
}
