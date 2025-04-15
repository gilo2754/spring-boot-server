package com.pluralsight.controller;

import com.pluralsight.config.TestSecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import(TestSecurityConfig.class)
@Disabled("Temporarily disabled due to configuration issues")
public class AdminControllerWebLayerTest {

    private static final String BASE_URL = "/admin/api/v1";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get(BASE_URL + "/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello, World"));
    }

    @Test
    public void testGetSpecialities() throws Exception {
        mockMvc.perform(get(BASE_URL + "/specialities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\"CARDIOLOGY\",\"DERMATOLOGY\",\"ENT\",\"GYNECOLOGY\",\"NEUROLOGY\",\"OPHTHALMOLOGY\",\"ORTHOPEDICS\",\"PEDIATRICS\",\"PSYCHIATRY\",\"UROLOGY\"]"));
    }

    @Test
    public void testGetServerStatus() throws Exception {
        mockMvc.perform(get(BASE_URL + "/server/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("Server is running"));
    }
}
