package com.pluralsight.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    private Long id;

    @Column(name = "clinic_name", nullable = false)
    private String clinic_name;

    @Column
    @Size(max = 200)
    private String description;

    @Column
    @Size(max = 200)
    private String address;

    @NotBlank
    @Column(name = "clinic_phone_number")
    @Size(max = 20)
    private String clinic_phone_number;

}
