package com.pluralsight.service;

import com.pluralsight.entity.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor createDoctor(Doctor doctor);
    List<Doctor> listDoctors();
    Doctor findDoctor(long id);

    void deleteDoctor(long id);

    Doctor updateDoctor(Long doctorId, Doctor doctor);

}
