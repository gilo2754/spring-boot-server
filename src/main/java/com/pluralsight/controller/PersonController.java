package com.pluralsight.controller;

import com.pluralsight.entity.User;
import com.pluralsight.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PersonController {
    @Autowired
    private UserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Optional> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        Optional user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
       // user.setPassword("");
        return ResponseEntity.ok(user);
    }

    //Retrieve people in general, Doctors and Patients using their person_type
    @GetMapping("/people")
    public List<User> getPeople(@RequestParam(name= "person_type", required = false) String person_type) {

        if(person_type !=  null)  {
            // Return people by type
            List<User> peopleByType = userService.listPersonByRole(person_type);
            if (peopleByType.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No people found by this type: " + person_type);
            }
            return peopleByType;
        } else {
            // Return list of all clinics
            List<User> allPeople = this.userService.listPerson();
            return allPeople;
        }
    }

    @PostMapping("/person/add")
    public ResponseEntity<?> createPerson(@RequestBody User person) {
        try {
            // Set the password for the person (assuming it is passed in the request body)
            String plainPassword = person.getPassword();
            String encryptedPassword = passwordEncoder.encode(plainPassword);
            person.setPassword(encryptedPassword);

            // Save the person
            User createdPerson = userService.createPerson(person);

            // Return the created person with a status code of 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
        } catch (Exception e) {
            // Handle the exception
            String errorMessage = "An error occurred while creating the person.";

            // Optionally, you can log the error for debugging purposes
            logger.error(errorMessage, e);

            // Return an error response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
