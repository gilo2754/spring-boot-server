package com.pluralsight.repository;

import java.util.List;
import java.util.Optional;

import com.pluralsight.enums.Speciality;
import org.springframework.data.repository.CrudRepository;

import com.pluralsight.entity.Clinic;
import org.springframework.stereotype.Repository;

@Repository

public interface ClinicRepository extends CrudRepository<Clinic, Long> {

    //Should this be in a
    List<Clinic> findBySpeciality(String speciality);
}
