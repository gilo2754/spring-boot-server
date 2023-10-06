package com.pluralsight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pluralsight.enums.Role;
import com.pluralsight.enums.Speciality;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Entity
@Table(name = "_user")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
//@DiscriminatorValue("USER")
//Probably this entity will be renamed by User
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // name = "user_id")
    private Long user_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")//, updatable = false, insertable = false)
    private Role role;

  @NotBlank
    @Column(name = "username")
    private String username;

    //@NotBlank
    //@Size(max = 50)
    @Column(name = "password")
    private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
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

    //Attributes for Doctors
    @NotNull
    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

  @Column(name = "availability")
  private LocalTime availability;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "clinic_id")
  private Clinic clinic_id;

  // ...for Patients
  @NotBlank
  @Column(name = "social_number")
  private String social_number;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

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
