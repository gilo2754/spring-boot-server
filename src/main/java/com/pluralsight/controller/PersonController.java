package com.pluralsight.controller;

import com.pluralsight.DTO.UserDTO;
import com.pluralsight.entity.Address;
import com.pluralsight.entity.User;
import com.pluralsight.enums.Role;
import com.pluralsight.repository.UserRepository;
import com.pluralsight.service.ClinicService;
import com.pluralsight.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PersonController {
    @Autowired
    private UserService userService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

      @GetMapping("/user-info")
      public ResponseEntity<Optional<UserDTO>> getUserInfo(@RequestHeader(name = "Authorization") String jwtToken) {
          String token = jwtToken.substring(7); // Elimina el prefijo "Bearer "
          String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
          Optional<UserDTO> user = userService.getUserByUsernameDTO(username);

          if (user != null) {
              return ResponseEntity.ok(user); // Devuelve la información del usuario como JSON
          } else {
              return ResponseEntity.notFound().build(); // Devuelve una respuesta 404 si el usuario no se encuentra
          }    }

    //Retrieve people in general, Doctors and Patients using their role
    @GetMapping("/people")
    public List<User> getPeople(@RequestParam(name= "role", required = false) String role) {

        if (role !=  null)  {
            try {
                Role enumRole = Role.valueOf(role.toUpperCase()); // Convierte el nombre en un valor de enumerado
                // Continúa con el procesamiento usando enumRole
                List<User> peopleByRole = userService.listPersonByRole(enumRole);
                if (peopleByRole.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No people found by this role: " + role);
                }
                return peopleByRole;
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role: " + role);
            }
        } else {
            // Return list of all users
            List<User> allPeople = this.userService.listPerson();
            return allPeople;
        }
    }

    @GetMapping("/peopleDTO")
    public List<UserDTO> getPeopleDTO(@RequestParam(name= "role", required = false) String role) {

        if (role !=  null)  {
            try {
                Role enumRole = Role.valueOf(role.toUpperCase()); // Convierte el nombre en un valor de enumerado
                // Continúa con el procesamiento usando enumRole
                List<UserDTO> peopleByRole = userService.listPersonByRole(enumRole);
                if (peopleByRole.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No people found by this role: " + role);
                }
                return peopleByRole;
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role: " + role);
            }
        } else {
            // Return list of all users
            List<UserDTO> allPeople = this.userService.listPersonDTO();
            return allPeople;
        }
    }

    @PostMapping("/person/add")
    public ResponseEntity<?> createPerson(@RequestBody User person) {
        try {
            // Validar que se proporcionen todos los campos requeridos
            List<String> missingFields = new ArrayList<>();

            if (person.getUsername() == null) {
                missingFields.add("username");
            }
            if (person.getPassword() == null) {
                missingFields.add("password");
            }
            if (person.getFirstName() == null) {
                missingFields.add("firstName");
            }
            if (person.getLastName() == null) {
                missingFields.add("lastName");
            }
            if (person.getEmail() == null) {
                missingFields.add("email");
            }
            if (person.getPhoneNumber() == null) {
                missingFields.add("phoneNumber");
            }
            if (person.getDateOfBirth() == null) {
                missingFields.add("dateOfBirth");
            }
            if (person.getSocialNumber() == null) {
                missingFields.add("socialNumber");
            }
            if (person.getRole() == null) {
                missingFields.add("role");
            }

            if (!missingFields.isEmpty()) {
                // Construir un mensaje de error que enumere los campos faltantes
                String errorMessage = "Faltan campos obligatorios. Asegúrese de proporcionar los siguientes campos: "
                        + String.join(", ", missingFields);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }

            // Set the password for the person (assuming it is passed in the request body)
            String plainPassword = person.getPassword();
            String encryptedPassword = passwordEncoder.encode(plainPassword);
            person.setPassword(encryptedPassword);

            // Create an Address object if address data is provided
            if (person.getAddress() != null) {
                // Validar que se proporcionen todos los campos requeridos para la dirección
                Address address = person.getAddress();
                List<String> missingAddressFields = new ArrayList<>();


                if (address.getStreet() == null) {
                    missingAddressFields.add("street");
                }
                if (address.getNeighborhood() == null) {
                    missingAddressFields.add("neighborhood");
                }
                if (address.getCity() == null) {
                    missingAddressFields.add("city");
                }
                if (address.getDepartment() == null) {
                    missingAddressFields.add("department");
                }
                if (address.getPostalCode() == null) {
                    missingAddressFields.add("postalCode");
                }
                if (address.getAdditionalInfo() == null) {
                    missingAddressFields.add("additionalInfo");
                }

                if (!missingAddressFields.isEmpty()) {
                    // Construir un mensaje de error que enumere los campos faltantes para la dirección
                    String errorMessage = "Faltan campos obligatorios para la dirección. Asegúrese de proporcionar los siguientes campos: "
                            + String.join(", ", missingAddressFields);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
                }
            }
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

    @PutMapping("/person/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updatedUser) {
        UserDTO user = userService.updateUser( updatedUser);

        if (user == null) {
            // Si no se encuentra el usuario con el userId especificado, puedes devolver una respuesta apropiada, por ejemplo, un 404 Not Found.
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/clinic/{clinic_id}/doctors")
    public Set<User>
    getDoctorsByClinicId(@PathVariable Long clinic_id) {
        return userService.getDoctorsByClinicId(clinic_id);
    }


}
