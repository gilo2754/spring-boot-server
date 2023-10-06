package com.pluralsight.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pluralsight.enums.ClinicState;
import com.pluralsight.enums.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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


//    @NotNull
    @Column(name = "clinic_name")
    private String clinic_name;

    @Column(name="clinic_description")
    @Size(max = 200)
    private String clinic_description;

    @Column(name="clinic_address")
   //@NotBlank
    @Size(max = 200)
    private String clinic_address;

    @NotBlank
    @Column(name = "clinic_phone_number")
    @Size(max = 20)
    private String clinic_phone_number;

    @NotNull
    //Not status to be able to use state(singular) and states(plural)
    @Enumerated(EnumType.STRING)
    private ClinicState clinic_state = ClinicState.IN_REVIEW;

  //  @NotNull
    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

//    @NotNull
    @Column(name = "opening_time")
    private LocalTime openingTime;

    //@NotNull
    @Column(name = "closing_time")
    private LocalTime closingTime;

    // Avoiding circular reference
    @JsonIgnore
    @OneToMany(mappedBy = "clinic_id")
    private List<User> doctors;



    //FIXME: Contructor really needed?
   /* public Clinic(String s, String s1, Speciality pediatria) {
    }
    */

    public Clinic(String clinic_name, String clinic_address, Speciality speciality) {
        this.clinic_name = clinic_name;
        this.clinic_address = clinic_address;
        this.speciality = speciality;

    }



    public boolean isOpen(LocalTime time) {
        return !time.isBefore(openingTime) && time.isBefore(closingTime);
    }

}
