package com.pluralsight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pluralsight.entity.User;
import com.pluralsight.exception.ClinicNotFoundException;
import com.pluralsight.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin/api/v1")
@AllArgsConstructor
public class AdminController {
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> list = this.userService.listUsers();
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        this.userService.addUser(user);
        System.out.println("New user was created");

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<User> deteleUser(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        System.out.println("User with id: " + id + " was deleted");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    // Just roles(@{ApplicationUserRole}) which contains those authorities can access hier
    // @PreAuthorize("hasAuthority('user:read')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")

    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<User>(this.userService.findUser(id), HttpStatus.OK);
        } catch (ClinicNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

}