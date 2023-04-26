package com.pluralsight.entity;
import com.pluralsight.enums.Speciality;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@DiscriminatorValue("doctor")
public class Doctor extends Person {

  //  @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // @Column(name = "doctor_id")
   // private Long doctor_id;

    @NotNull
    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private Speciality speciality;


    @Column(name = "availability")
    private String availability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor")
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
