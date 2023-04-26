package com.pluralsight.service;

import com.pluralsight.entity.Doctor;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.exception.DoctorNotFoundException;
import com.pluralsight.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        this.doctorRepository.save(doctor);
        return doctor;
    }

    @Transactional(readOnly = true)
    public List<Doctor> listDoctors() {
        return (List<Doctor>) this.doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Doctor findDoctor(long id) {
        Optional<Doctor> optionalDoctor = this.doctorRepository.findById(id);

        if (optionalDoctor.isPresent())
            return optionalDoctor.get();
        else
            // TODO: Adapt to the right Exception
            throw new ClinicNotFoundException("Patient Not Found");
    }

    @Transactional
    public void deleteDoctor(long id) {
        Optional<Doctor> optionalDoctor = this.doctorRepository.findById(id);

        if (optionalDoctor.isPresent()) {
            this.doctorRepository.deleteById(id);
        } else
            throw new ClinicNotFoundException("User with id " + id + "to delete Not Found");

    }

    @Transactional
    public Doctor updateDoctor(Long doctorId, Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id " + doctorId));

        // Update existingDoctor details
        existingDoctor.setFirst_name(updatedDoctor.getFirst_name());
        existingDoctor.setLast_name(updatedDoctor.getLast_name());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setPhone_number(updatedDoctor.getPhone_number());
        existingDoctor.setDateOfBirth(updatedDoctor.getDateOfBirth());
        existingDoctor.setSpeciality(updatedDoctor.getSpeciality());
        return doctorRepository.save(existingDoctor);
    }


}
