package com.pluralsight.controller;

import com.pluralsight.entity.Person;
import com.pluralsight.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PersonController<T extends Person> {
    @Autowired
    private PersonService<Person> personService;

//Retrieve people in general, Doctors and Patients using their person_type
    @GetMapping("/people")
    public List<Person> getPeople(@RequestParam(name= "person_type", required = false) String person_type) {

        if(person_type !=  null)  {
            // Return people by type
            List<Person> peopleByType = personService.listPersonByType(person_type);
            if (peopleByType.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No people found by this type: " + person_type);
            }
            return peopleByType;
        } else {
            // Return list of all clinics
            List<Person> allPeople = this.personService.listPerson();
            return allPeople;
        }
    }
}
