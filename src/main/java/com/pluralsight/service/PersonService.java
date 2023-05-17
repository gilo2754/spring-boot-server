package com.pluralsight.service;

import com.pluralsight.entity.Person;

import java.util.List;

public interface PersonService<P extends Person> {
    List<Person> listPerson();
    List<Person> listPersonByType(String person_type);

}
