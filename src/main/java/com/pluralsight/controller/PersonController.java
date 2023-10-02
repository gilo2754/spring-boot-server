package com.pluralsight.controller;

import com.pluralsight.entity.User;
import com.pluralsight.enums.Role;
import com.pluralsight.repository.UserRepository;
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
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PersonController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

   /* @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Verificar los roles y permisos del usuario autenticado
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_DOCTOR"))) {
                // El usuario tiene el rol de doctor, permite el acceso a la información
                // ...
            } else {
                // El usuario no tiene el permiso necesario, devuelve un 403
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para acceder a esta información. 403");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debes iniciar sesión para acceder a esta información. 401");
        }
        return null;
    }
*/

      /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Optional user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
       // user.setPassword("");

        return ResponseEntity.ok(user);*/
    //@PreAuthorize("hasRole('DOCTOR')")
    //@PreAuthorize("hasAuthority('doctor:read')")
      @GetMapping("/user-info")
      public ResponseEntity<Optional<User>> getUserInfo(@RequestHeader(name = "Authorization") String jwtToken) {
          String token = jwtToken.substring(7); // Elimina el prefijo "Bearer "
          String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
          Optional<User> user = userRepository.findByUsername(username);

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
           /* if (person.getSocial_number() == null) {
                missingFields.add("social_number");
            }*/
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
