package com.pluralsight.service;

import com.pluralsight.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService<P extends User> {
    List<User> listPerson();
    List<User> listPersonByRole(String person_type);
    Optional<User> getPersonById(Long personId);

    P createPerson(P person);

    Optional<User> getUserByUsername(String username);
}
