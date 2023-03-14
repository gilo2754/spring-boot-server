package com.pluralsight.service;

import java.util.List;

import com.pluralsight.entity.Clinic;

public interface ClinicService {
    List<Clinic> listClinics();

    Clinic findClinic(long id);

    void deleteClinic(long id);

    Clinic addClinic(Clinic clinic);

}
