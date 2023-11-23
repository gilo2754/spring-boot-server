package com.pluralsight.DTO;

import com.pluralsight.enums.Role;
import com.pluralsight.enums.Speciality;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long user_id;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Speciality speciality;
    private LocalTime availability;
    private Set<String> clinicNames; // Assuming you only need clinic names

    // Additional attributes for Patients
    private String socialNumber;

    // Additional attributes for Doctors
    private String dui;

    // No password field in DTO for security reasons

    // Constructor sin argumentos necesario para ModelMapper
    public UserDTO() {
    }

    public UserDTO(Long user_id, Role role, String username, String firstName, String lastName, String email,
                   String phoneNumber, LocalDate dateOfBirth, Speciality speciality, LocalTime availability,
                   Set<String> clinicNames, String socialNumber, String dui) {
        this.user_id = user_id;
        this.role = role;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.speciality = speciality;
        this.availability = availability;
        this.clinicNames = clinicNames;
        this.socialNumber = socialNumber;
        this.dui = dui;
    }

    // Getters and setters as needed
}
