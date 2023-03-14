package com.pluralsight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.entity.Clinic;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.repository.ClinicRepository;

@Service
public class ClinicServiceImpl implements ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public List<Clinic> listClinics() {
        return (List<Clinic>) this.clinicRepository.findAll();
    }

    @Override
    public Clinic findClinic(long id) {
        Optional<Clinic> optionalApplication = this.clinicRepository.findById(id);

        if (optionalApplication.isPresent())
            return optionalApplication.get();
        else
            throw new ClinicNotFoundException("Clinic Not Found");
    }

    @Override
    public void deleteClinic(long id) {
        Optional<Clinic> optionalClinic = this.clinicRepository.findById(id);

        if (optionalClinic.isPresent()) {
            this.clinicRepository.deleteById(id);
        } else
            throw new ClinicNotFoundException("Clinic with id " + id + "to delete Not Found");
    }

    @Override
    public Clinic addClinic(Clinic clinic) {
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

}
