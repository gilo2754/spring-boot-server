package com.pluralsight.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.entity.User;
import com.pluralsight.exception.ApplicationNotFoundException;
import com.pluralsight.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    public User findUser(long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            // TODO: Adapt to the right Exception
            throw new ApplicationNotFoundException("User Not Found");
    }

    @Override
    public void addUser(User user) {
        System.out.println(user);
        // Optional<Application> optionalApplication = this.applicationRepository.findById(application.));
        // Optional<Application> optionalApplication = this.applicationRepository.findById(id);
        //
        // } else
        // throw new ApplicationNotFoundException("Application with id " + id + "already exist");
        // }
        // TODO add some rule to avoid duplication
        this.userRepository.save(user);

    }

    @Override
    public void deleteUser(long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            this.userRepository.deleteById(id);
        } else
            throw new ApplicationNotFoundException("User with id " + id + "to delete Not Found");

    }

}
