package com.pluralsight.repository;

import com.pluralsight.entity.Patient;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    // @Query("SELECT i FROM Application WHERE i.name = ?1")
    // Optional<User> findUserByName(String name);
}
