package com.pluralsight.service;

import com.pluralsight.entity.Person;
import com.pluralsight.exception.PersonNotFoundException;
import com.pluralsight.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Person createOrUpdatePerson(Person person) {
        // Obtener la contraseña sin encriptar de la persona
        String plainPassword = person.getPassword();

        // Encriptar la contraseña utilizando el PasswordEncoder
        String encryptedPassword = passwordEncoder.encode(plainPassword);

        // Establecer la contraseña encriptada en la persona
        person.setPassword(encryptedPassword);

        // Resto del código para crear o actualizar la persona
        return this.personRepository.save(person);

    }

    @Transactional(readOnly = true)
    public List<Person> listPerson() {
        return (List<Person>) this.personRepository.findAll();
    }

    @Transactional
    public List<Person> listPersonByRole(String role) {
        return personRepository.findByRole(role)
                .stream()
                //avoid NPEs
                .filter(person -> person != null && person.getRole() != null && person.getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Person> getPersonById(Long personId) {
        Optional<Person> optionalPerson = this.personRepository.findById(personId);

        if (optionalPerson.isPresent()) {
            return optionalPerson;
        } else
            throw new PersonNotFoundException("Person with id " + personId + " Not Found");
    }

    @Transactional
    public Person createPerson(Person person) {
        return this.personRepository.save(person);
    }

    @Transactional
    public Optional<Person> getUserByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    /*
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
*/

}
