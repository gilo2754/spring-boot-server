package com.pluralsight.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pluralsight.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Appointment implements Serializable {
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    private Long appointment_id;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    //@JsonIgnore
    // TODO maybe this is not needed, because you can go from clinic to doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NotNull
    @Column(name = "appointment_status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointment_status = AppointmentStatus.PENDING;

}
