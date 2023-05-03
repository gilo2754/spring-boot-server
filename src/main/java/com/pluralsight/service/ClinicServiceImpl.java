package com.pluralsight.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.entity.Clinic;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.repository.ClinicRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicServiceImpl implements ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Transactional(readOnly = true)
    public List<Clinic> listClinics() {
        return (List<Clinic>) this.clinicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Clinic getClinicById(long id) {
        Optional<Clinic> optionalApplication = this.clinicRepository.findById(id);

        if (optionalApplication.isPresent())
            return optionalApplication.get();
        else
            throw new ClinicNotFoundException("Clinic Not Found");
    }

    @Transactional
    public void deleteClinic(long id) {
        Optional<Clinic> optionalClinic = this.clinicRepository.findById(id);

        if (optionalClinic.isPresent()) {
            this.clinicRepository.deleteById(id);
        } else
            throw new ClinicNotFoundException("Clinic with id " + id + "to delete Not Found");
    }

    @Transactional
    public Clinic createClinic(Clinic clinic) {
        System.out.println(clinic);
        // Optional<Application> optionalApplication = this.applicationRepository.findById(application.));
        // Optional<Application> optionalApplication = this.applicationRepository.findById(id);
        //
        // } else
        // throw new ApplicationNotFoundException("Application with id " + id + "already exist");
        // }
        // TODO add some rule to avoid duplication
        this.clinicRepository.save(clinic);
        return clinic;

    }

    @Transactional
    public Clinic update(Long clinicId, Clinic updatedClinic) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ClinicNotFoundException("Clinic not found with id " + clinicId));
        clinic.setClinic_name(updatedClinic.getClinic_name());
        clinic.setClinic_description(updatedClinic.getClinic_description());
        clinic.setClinic_state(updatedClinic.getClinic_state());
        clinic.setClinic_address(updatedClinic.getClinic_address());
        clinic.setClinic_phone_number(updatedClinic.getClinic_phone_number());
        clinic.setClinic_name(updatedClinic.getClinic_name());
        clinic.setSpeciality(updatedClinic.getSpeciality());
        clinic.setOpeningTime(updatedClinic.getOpeningTime());
        clinic.setClosingTime(updatedClinic.getClosingTime());
        return clinicRepository.save(clinic);
    }

    //fixme; this return all clinics
    @Transactional
    public List<Clinic> getClinicsBySpeciality(String speciality) {
        return clinicRepository.findBySpeciality(speciality)
                .stream()
                //avoid NPEs
                .filter(clinic -> clinic != null && clinic.getSpeciality() != null && clinic.getSpeciality().equals(speciality))
                .collect(Collectors.toList());
    }



}
