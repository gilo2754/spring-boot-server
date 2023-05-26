package com.pluralsight.entity;

import com.pluralsight.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table//(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
//Probably this entity will be renamed by User
public class Person implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // name = "user_id")
    private Long person_id;

  //  @Getter(AccessLevel.NONE)
  //  @Column(name = "personType", insertable = false, updatable = false)
    //private String personType = PersonType.DOCTOR.toString();

  /*
  @Column(name = "personType", insertable = false, updatable = false)
  private String personType;// = PersonType.DOCTOR.toString();
*/
  @Enumerated(EnumType.STRING)
  @Column(name = "role", insertable = false, updatable = false)
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

    /*
    @JsonIgnore // Exclude the password field from JSON serialization
    public String getPassword() {
    return password;
    }
*/
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

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
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
