package com.pluralsight.entity;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pluralsight.enums.ClinicState;
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
   // @NotBlank
    @Size(max = 200)
    private String clinic_address;

    @NotBlank
    @Column(name = "clinic_phone_number")
    @Size(max = 20)
    private String clinic_phone_number;

    @NotNull
    @Column(name = "clinic_state")
    //Not status to be able to use state(singular) and states(plural)
    @Enumerated(EnumType.STRING)
    private ClinicState clinic_state = ClinicState.IN_REVIEW;

//    @NotNull
    @Column(name = "opening_time")
    private LocalTime openingTime;

    //@NotNull
    @Column(name = "closing_time")
    private LocalTime closingTime;

    public boolean isOpen(LocalTime time) {
        return !time.isBefore(openingTime) && time.isBefore(closingTime);
    }

}
