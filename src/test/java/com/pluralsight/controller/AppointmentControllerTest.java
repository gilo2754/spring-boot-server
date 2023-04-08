package com.pluralsight.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pluralsight.service.ClinicService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClinicService clinicService;

    @Test
    public void getAllApplications() throws Exception {

        // Create a post request with an accept header for application\json
        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.get("/tza/applications/").contentType(MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert that the return status is OK
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void getAllTickets() throws Exception {

        // Create a post request with an accept header for application\json
        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.get("/tza/tickets/").contentType(MediaType.APPLICATION_JSON);

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert that the return status is OK
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // TODO Test getApplicationById
    // TODO test new API End-Points
}