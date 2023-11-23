package com.pluralsight.service;

import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.enums.Role;
import com.pluralsight.enums.Speciality;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClinicServiceImpl implements ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private UserService userService;

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
        List<User> doctorsToAdd = new ArrayList<>();

        for (User doctor : clinic.getDoctors()) {
            Long doctorId = doctor.getUser_id();

            if (userService.getPersonById(doctorId) == null) {
                // El médico no existe
                throw new IllegalArgumentException("El médico con user_id " + doctorId + " no existe.");
            }

            if (!doctor.getRole().equals(Role.DOCTOR)) {
                // El usuario no es un médico válido
                throw new IllegalArgumentException("El usuario con user_id " + doctorId + " no es un médico válido.");
            }

            doctorsToAdd.add(doctor);
        }

        // Verificamos si la lista de médicos está vacía o no
        if (!doctorsToAdd.isEmpty()) {
          //  clinic.getDoctors().clear();
            clinic.getDoctors().addAll(doctorsToAdd);
        }


        return this.clinicRepository.save(clinic);
    }

    @Transactional
    public Clinic updateClinic(Long clinicId, Clinic updatedClinic) {
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
        clinic.setDoctors(updatedClinic.getDoctors());
        return clinicRepository.save(clinic);
    }

    //fixme; this return all clinics
    @Transactional
    public List<Clinic> getClinicsBySpeciality(Speciality speciality) {
        return clinicRepository.findBySpeciality(speciality.getValue())
                .stream()
                //avoid NPEs
                .filter(clinic -> clinic != null && clinic.getSpeciality() != null && clinic.getSpeciality().equals(speciality))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Clinic> getClinicsForCurrentUser(Long userId) {
        return clinicRepository.findClinicsByUserId(userId);
    }

}
