package com.pluralsight.repository;

import com.pluralsight.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByRole(String role);

    Optional<Person> findByUsername(String username);
}
