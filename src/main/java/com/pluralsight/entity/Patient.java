package com.pluralsight.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "PATIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DiscriminatorValue("PATIENT")
public class Patient extends User implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(nullable = false) // name = "user_id")
    //private Long patient_id;

    @NotBlank
    @Column(name = "social_number")
    private String social_number;

    //@JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    /*
    public Patient(Long person_id, String first_name, String last_name, String email, String phone_number, LocalDate dateOfBirth, String social_number) {
        super(person_id, first_name, last_name, email, phone_number, dateOfBirth);
        this.social_number = social_number;
    }
    */
}
