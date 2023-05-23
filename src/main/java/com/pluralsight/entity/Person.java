package com.pluralsight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pluralsight.enums.PersonType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table//(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "personType", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // name = "user_id")
    private Long person_id;

  //  @Getter(AccessLevel.NONE)
  //  @Column(name = "personType", insertable = false, updatable = false)
    //private String personType = PersonType.DOCTOR.toString();

  @Column(name = "personType", insertable = false, updatable = false)
  private String personType;// = PersonType.DOCTOR.toString();

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    //@Size(max = 50)
    private String password;

    @JsonIgnore // Exclude the password field from JSON serialization
    public String getPassword() {
    return password;
    }

    @NotBlank
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    /*
    public Person(Long person_id, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.person_id = person_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
    */
}
