package com.pluralsight.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pluralsight.entity.Application;
import com.pluralsight.entity.Ticket;
import com.pluralsight.exception.ApplicationNotFoundException;
import com.pluralsight.service.ApplicationService;
import com.pluralsight.service.TicketService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TzaController {
    private ApplicationService applicationService;

    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> list = this.ticketService.listTickets();
        return new ResponseEntity<List<Ticket>>(list, HttpStatus.OK);
    }

    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> list = this.applicationService.listApplications();
        return new ResponseEntity<List<Application>>(list, HttpStatus.OK);
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<Application>(this.applicationService.findApplication(id),
                HttpStatus.OK);
        } catch (ApplicationNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application Not Found");
        }
    }

    @PostMapping("/application/add")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        // public Application createApplication(@RequestBody Application application) {
        this.applicationService.addApplication(application);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/application/{id}")
    public ResponseEntity<Application> deteleApplication(@PathVariable("id") long id) {
        this.applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

}