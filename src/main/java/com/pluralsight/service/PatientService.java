package com.pluralsight.service;

import java.util.List;

import com.pluralsight.entity.Patient;

public interface PatientService {

    Patient createPatient(Patient patient);
    List<Patient> listPatients();
    Patient findPatient(long id);

    void deletePatient(long id);

    Patient updatePatient(Long patientId, Patient patient);

}
