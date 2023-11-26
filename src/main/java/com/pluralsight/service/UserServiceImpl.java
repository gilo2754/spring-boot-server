package com.pluralsight.service;

import com.pluralsight.DTO.UserDTO;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.enums.Role;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.exception.UserNotFoundException;
import com.pluralsight.repository.ClinicRepository;
import com.pluralsight.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClinicRepository clinicRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createOrUpdatePerson(User user) {
        // Obtener la contraseña sin encriptar de la persona
        String plainPassword = user.getPassword();

        // Encriptar la contraseña utilizando el PasswordEncoder
        String encryptedPassword = passwordEncoder.encode(plainPassword);

        // Establecer la contraseña encriptada en la persona
        user.setPassword(encryptedPassword);

        // Resto del código para crear o actualizar la persona
        return this.userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public List<User> listPerson() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    public List<UserDTO> listPersonDTO() {
        List<User> userList = (List<User>) userRepository.findAll();

        // Utilizar stream y map para convertir la lista de User a UserDTO
        return userList.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());    }

    @Transactional
    public List<User> listPersonByRole(Role role) {
        return userRepository.findByRole(String.valueOf(role))
                .stream()
                //avoid NPEs
                .filter(person -> person != null && person.getRole() != null && person.getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<User> getPersonById(Long personId) {
        Optional<User> optionalPerson = this.userRepository.findById(personId);

        if (optionalPerson.isPresent()) {
            return optionalPerson;
        } else
            throw new UserNotFoundException("Person with id " + personId + " Not Found");
    }

    @Transactional
    public User createPerson(User person) {
        return this.userRepository.save(person);
    }

    @Transactional
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> getUserByUsernameDTO(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public User updateUser(User updatedUser) {
        // Primero, verifica si el usuario con el ID especificado existe en tu sistema.
        User existingUser = userRepository.findById(updatedUser.getUser_id()).orElse(null);

        if (existingUser == null) {
            // El usuario no existe, puedes manejar este caso de alguna manera, por ejemplo, lanzar una excepción.
            throw new UserNotFoundException("El usuario no se encontró con el ID especificado");
        }

        // Realiza las actualizaciones de los campos que desees permitir modificar
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        // Continúa actualizando otros campos según sea necesario

        // Luego, guarda el usuario actualizado en tu base de datos
         updatedUser = userRepository.save(existingUser);

        return updatedUser;
    }

    @Override
    public Set<User> getDoctorsByClinicId(Long clinicId) {
        Optional<Clinic> clinicOptional = clinicRepository.findById(clinicId);
        if (clinicOptional.isPresent()) {
            Clinic clinic = clinicOptional.get();
            return clinic.getDoctors();
        } else {
            throw new ClinicNotFoundException("Clinic with id " + clinicId + " not found");
        }
    }

}
