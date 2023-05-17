package com.pluralsight.controller;

import com.pluralsight.enums.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(controllers = AdminController.class)
public class AdminControllerWebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    //In this test, the full Spring application context is started but without the server. We can narrow the tests to only the web layer by using @WebMvcTest
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/admin/api/v1/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }


    @Test
    public void testGetSpecialities() throws Exception {
        // Arrange
        List<String> expectedSpecialities = Arrays.stream(Speciality.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        // Act
        mockMvc.perform(get("/admin/api/v1/specialities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedSpecialities.size())))
                .andExpect(jsonPath("$", containsInAnyOrder(expectedSpecialities.toArray())))
                .andReturn();
    }
}
