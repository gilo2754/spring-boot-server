package com.pluralsight.service;

import com.pluralsight.DTO.ClinicDTO;
import com.pluralsight.entity.Clinic;
import com.pluralsight.enums.Speciality;

import java.util.List;

public interface ClinicService {
    List<Clinic> listClinics();

    Clinic getClinicById(long id);

    void deleteClinic(long id);

    Clinic createClinic(Clinic clinic);

    Clinic updateClinic(Long clinicId, Clinic clinic);

    List<Clinic> getClinicsBySpeciality(Speciality speciality);

    List<ClinicDTO> getClinicsForCurrentUser(Long userId);
}
