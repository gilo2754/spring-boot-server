package com.pluralsight.service;

import java.util.List;

import com.pluralsight.entity.User;

public interface UserService {
    List<User> listUsers();

    User findUser(long id);

    /**
     * @param user
     */
    void addUser(User user);

    /**
     * @param id
     */
    void deleteUser(long id);

}
