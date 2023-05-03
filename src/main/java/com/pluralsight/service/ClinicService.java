package com.pluralsight.service;

import java.util.List;

import com.pluralsight.entity.Clinic;

public interface ClinicService {
    List<Clinic> listClinics();

    Clinic getClinicById(long id);

    void deleteClinic(long id);

    Clinic createClinic(Clinic clinic);

    Clinic update(Long clinicId, Clinic clinic);

    List<Clinic> getClinicsBySpeciality(String speciality);
}
