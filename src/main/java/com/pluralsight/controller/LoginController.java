package com.pluralsight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Return the login.html view
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        // Perform authentication logic here
        // You can use Spring Security's authentication mechanisms or your custom authentication logic

        // If authentication is successful, redirect the user to the home page
        return "redirect:/me";

        // If authentication fails, you can return an error view or redirect the user back to the login page with an error message
    }
}

