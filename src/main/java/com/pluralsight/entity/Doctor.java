package com.pluralsight.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pluralsight.enums.Speciality;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@DiscriminatorValue("DOCTOR")
public class Doctor extends User implements Serializable {

  //  @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // @Column(name = "doctor_id")
   // private Long doctor_id;

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

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    /*
    public Doctor(Long person_id, String first_name, String last_name, String email, String phone_number, LocalDate dateOfBirth, Speciality speciality, Clinic clinic, List<Appointment> appointments) {
        super(person_id, first_name, last_name, email, phone_number, dateOfBirth);
        this.speciality = speciality;
        this.clinic = clinic;
        this.appointments = appointments;
    }
*/
    //  @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // private Set<Appointment> appointments = new HashSet<>();

    // constructors, getters and setters, other methods
}
