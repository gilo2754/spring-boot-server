package com.pluralsight.controller;

import com.pluralsight.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AppointmentControllerTest {
    @MockBean
    AppointmentService appointmentService;
    @Test // Sanity check
    public void contextLoads() throws Exception {
        assertThat(appointmentService).isNotNull();
    }
    // TODO Test getApplicationById
    // TODO test new API End-Points
}
