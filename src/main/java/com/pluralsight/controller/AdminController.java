package com.pluralsight.controller;

import com.pluralsight.enums.Speciality;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/api/v1")
@AllArgsConstructor
public class AdminController {
   // private DoctorService doctorService;
//TODO: que pueden hacer solo los ADMINS?
   @GetMapping("/specialities")
   public ResponseEntity<List<String>> getSpecialities() {
       List<String> specialities = Arrays.stream(Speciality.values())
               .map(Enum::name)
               .collect(Collectors.toList());
       return new ResponseEntity<>(specialities, HttpStatus.OK);
   }

    @GetMapping("/server/status")
    public ResponseEntity<String> getServerStatus() {
        return ResponseEntity.ok("Server is running");
    }

}