package com.pluralsight.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {
    @GetMapping("/basicauth")
    public String basicAuth() {
        return "{ \"message\": \"Hello, world!\" }";

    }
}
