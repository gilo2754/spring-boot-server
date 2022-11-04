package com.pluralsight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.entity.Application;
import com.pluralsight.exception.ApplicationNotFoundException;
import com.pluralsight.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> listApplications() {
        return (List<Application>) this.applicationRepository.findAll();
    }

    @Override
    public Application findApplication(long id) {
        Optional<Application> optionalApplication = this.applicationRepository.findById(id);

        if (optionalApplication.isPresent())
            return optionalApplication.get();
        else
            throw new ApplicationNotFoundException("Application Not Found");
    }

    @Override
    public void deleteApplication(long id) {
        Optional<Application> optionalApplication = this.applicationRepository.findById(id);

        if (optionalApplication.isPresent()) {
            this.applicationRepository.deleteById(id);
        } else
            throw new ApplicationNotFoundException("Application with id " + id + "to delete Not Found");
    }

    @Override
    public Application addApplication(Application application) {
        System.out.println(application);
        // Optional<Application> optionalApplication = this.applicationRepository.findById(application.));
        // Optional<Application> optionalApplication = this.applicationRepository.findById(id);
        //
        // } else
        // throw new ApplicationNotFoundException("Application with id " + id + "already exist");
        // }
        // TODO add some rule to avoid duplication
        this.applicationRepository.save(application);
        return application;

    }

}
