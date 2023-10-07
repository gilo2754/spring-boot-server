package com.pluralsight.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String street;       // Calle y número
    private String neighborhood;  // Colonia
    private String city;          // Ciudad o municipio
    private String department;    // Departamento
    private String postalCode;    // Código Postal
    private String additionalInfo; // Referencias adicionales
}

