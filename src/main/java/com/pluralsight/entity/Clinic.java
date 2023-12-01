package com.pluralsight.entity;

import com.pluralsight.enums.ClinicState;
import com.pluralsight.enums.Speciality;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Clinic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clinic_id")
    private Long clinic_id;

    @Column(name = "clinic_name")
    private String clinic_name;

    @Column(name="clinic_description")
    @Size(max = 200)
    private String clinic_description;

    @Column(name="clinic_address")
    @Size(max = 200)
    private String clinic_address;

    //@NotBlank
    @Column(name = "clinic_phone_number")
    @Size(max = 20)
    private String clinic_phone_number;

    @NotNull
    //Not status to be able to use state(singular) and states(plural)
    @Enumerated(EnumType.STRING)
    private ClinicState clinic_state = ClinicState.IN_REVIEW;

    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                   // CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "clinic_doctors",
            joinColumns = { @JoinColumn(name = "clinic_id") },
            inverseJoinColumns = { @JoinColumn(name = "doctor_id") })
    private Set<User> doctors = new HashSet<>();

    public void addDoctor(User doctor) {
        this.doctors.add(doctor);
        doctor.getClinics().add(this);
    }

    //FIXME: Contructor really needed?

    public Clinic(String clinic_name, String clinic_address, Speciality speciality) {
        this.clinic_name = clinic_name;
        this.clinic_address = clinic_address;
        this.speciality = speciality;
    }

    public boolean isOpen(LocalTime time) {
        return !time.isBefore(openingTime) && time.isBefore(closingTime);
    }

     public Set<User> getDoctors() {
        return this.doctors();
    }

    private Set<User> doctors() {
        return doctors;
    }

}
