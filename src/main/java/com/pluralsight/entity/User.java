package com.pluralsight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pluralsight.enums.Role;
import com.pluralsight.enums.Speciality;
import lombok.Getter;
import lombok.Setter;
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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "_user")
@Getter
@Setter
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

    @NotBlank
    //@Size(max = 50)
    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

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
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
              //      CascadeType.PERSIST,
                    CascadeType.MERGE
            })

    @JsonIgnore
    private Set<Clinic> clinics = new HashSet<>();

    @Column(name = "dui")
    private String dui;
    // ...for Patients
    @Column(name = "socialNumber")
    private String socialNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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


}
