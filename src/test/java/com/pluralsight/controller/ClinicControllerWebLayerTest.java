package com.pluralsight.controller;

import com.pluralsight.config.TestSecurityConfig;
import com.pluralsight.entity.Clinic;
import com.pluralsight.service.ClinicService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClinicController.class)
@Import(TestSecurityConfig.class)
@Disabled("Temporarily disabled due to configuration issues")
class ClinicControllerWebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClinicService clinicService;

    @Test
    void testGetClinicsBySpecialty_WithoutSpecialty() throws Exception {
        // Mock the service response
        List<Clinic> allClinics = Arrays.asList(new Clinic(), new Clinic());
        given(clinicService.listClinics()).willReturn(allClinics);

        // Perform the GET request without a speciality parameter
        mockMvc.perform(get("/api/v1/clinic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
