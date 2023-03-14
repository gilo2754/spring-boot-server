package com.pluralsight.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // name = "user_id")
    private Long user_id;

    @NotBlank
    @Column
    private String patientName;

    @NotBlank
    @Size(max = 100)
    private String patientSurname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    private String patientPhoneNumber;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;


}
