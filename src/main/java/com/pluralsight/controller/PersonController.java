package com.pluralsight.controller;

import com.pluralsight.entity.Person;
import com.pluralsight.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

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

    @PostMapping("/person/add")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        // Set the password for the person (assuming it is passed in the request body)
        String plainPassword = person.getPassword();
        String encryptedPassword = passwordEncoder.encode(plainPassword);
        person.setPassword(encryptedPassword);

        // Save the person
        Person createdPerson = personService.createPerson(person);

        // Return the created person with a status code of 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }
}
