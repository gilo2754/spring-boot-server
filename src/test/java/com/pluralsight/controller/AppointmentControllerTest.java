package com.pluralsight.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.pluralsight.service.AppointmentService;
import com.pluralsight.service.ClinicService;

@SpringBootTest
class AppointmentControllerTest {
    @MockBean
    AppointmentService appointmentService;

    @Test
    void contextLoads() {
        assertThat(appointmentService).isNotNull();
    }
    // TODO Test getApplicationById
    // TODO test new API End-Points
}
