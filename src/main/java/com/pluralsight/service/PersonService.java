package com.pluralsight.service;

import com.pluralsight.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService<P extends Person> {
    List<Person> listPerson();
    List<Person> listPersonByType(String person_type);
    Optional<Person> getPersonById(Long personId);

    P createPerson(P person);
}
