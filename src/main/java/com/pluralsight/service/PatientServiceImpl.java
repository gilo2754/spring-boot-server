package com.pluralsight.service;

import java.util.List;
import java.util.Optional;

import com.pluralsight.entity.Patient;
import com.pluralsight.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.exception.ClinicNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public Patient createPatient(Patient patient) {
        this.patientRepository.save(patient);
        return patient;
    }

    @Transactional(readOnly = true)
    public List<Patient> listPatients() {
        return (List<Patient>) this.patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient findPatient(long id) {
        Optional<Patient> optionalPatient = this.patientRepository.findById(id);

        if (optionalPatient.isPresent())
            return optionalPatient.get();
        else
            // TODO: Adapt to the right Exception
            throw new ClinicNotFoundException("Patient Not Found");
    }

    @Transactional
    public void addPatient(Patient patient) {
        System.out.println(patient);
        // Optional<Application> optionalApplication = this.applicationRepository.findById(application.));
        // Optional<Application> optionalApplication = this.applicationRepository.findById(id);
        //
        // } else
        // throw new ApplicationNotFoundException("Application with id " + id + "already exist");
        // }
        // TODO add some rule to avoid duplication
        this.patientRepository.save(patient);

    }

    @Transactional
    public void deletePatient(long id) {
        Optional<Patient> optionalPatient = this.patientRepository.findById(id);

        if (optionalPatient.isPresent()) {
            this.patientRepository.deleteById(id);
        } else
            throw new ClinicNotFoundException("User with id " + id + "to delete Not Found");

    }

    @Transactional
    public Patient updatePatient(Long patientId, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ClinicNotFoundException("Patient not found with id " + patientId));


        // Update existingPatient details
        existingPatient.setFirst_name(updatedPatient.getFirst_name());
        existingPatient.setLast_name(updatedPatient.getLast_name());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPhone_number(updatedPatient.getPhone_number());
        existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
        existingPatient.setSocial_number(updatedPatient.getSocial_number());
        return patientRepository.save(existingPatient);
    }



}
