package com.pluralsight.controller;

import com.pluralsight.entity.Clinic;
import com.pluralsight.enums.Speciality;
import com.pluralsight.repository.DoctorRepository;
import com.pluralsight.service.ClinicService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = ClinicController.class)
public class ClinicControllerWebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private ClinicService clinicService;
    //In this test, the full Spring application context is started but without the server. We can narrow the tests to only the web layer by using @WebMvcTest

/* FIXME
    @Test
    public void testGetClinicsBySpecialty_WithSpecialty() throws Exception {
        // Mock the service response
        List<Clinic> clinicsBySpecialty = Arrays.asList(new Clinic(), new Clinic());
        Speciality anySpe =
        given(clinicService.getClinicsBySpeciality(any())).willReturn(clinicsBySpecialty);

        // Perform the GET request with a speciality parameter
        mockMvc.perform(get("/api/v1/clinic")
                        .param("Speciality", "PEDIATRIA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
*/
    @Test
    public void testGetClinicsBySpecialty_WithoutSpecialty() throws Exception {
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
