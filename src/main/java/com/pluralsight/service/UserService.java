package com.pluralsight.service;

import com.pluralsight.DTO.UserDTO;
import com.pluralsight.entity.Clinic;
import com.pluralsight.entity.User;
import com.pluralsight.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService<P extends User> {
    List<User> listPerson();

    List<UserDTO> listPersonDTO();

    List<User> listPersonByRole(Role person_type);
    Optional<User> getPersonById(Long personId);

    P createPerson(P person);

    User createDoctorWithClinic(User doctor, Clinic clinic);

    Optional<User> getUserByUsername(String username);

    Optional<UserDTO> getUserByUsernameDTO(String username);

    //UserDTO updateUser(User updatedUser);

    UserDTO updateUser(UserDTO updatedUserDTO);

    Set<User> getDoctorsByClinicId(Long clinicId);

    void updatePassword(String hashedPassword);
}
